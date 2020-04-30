package com.liazhan.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.BaseServiceImpl;
import com.liazhan.core.utils.DtoUtil;
import com.liazhan.core.utils.RedisAndDBTransactionUtil;
import com.liazhan.core.utils.TokenUtil;
import com.liazhan.member.consts.MemberConst;
import com.liazhan.member.dao.UserDao;
import com.liazhan.member.dao.UserTokenDao;
import com.liazhan.member.dao.entity.UserDO;
import com.liazhan.member.dao.entity.UserTokenDO;
import com.liazhan.member.input.dto.UserLoginInpDTO;
import com.liazhan.member.service.MemberLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @version:V1.0
 * @Description: 会员登录相关接口实现类
 * @author: Liazhan
 * @date 2020/4/28 9:24
 */
@RestController
@RefreshScope
public class MemberLoginServiceImpl extends BaseServiceImpl<JSONObject> implements MemberLoginService {

    @Value("${login.type.max}")
    private Integer loginTypeMax;
    @Value("${login.type.value}")
    private String loginTypeValue;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserTokenDao userTokenDao;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private RedisAndDBTransactionUtil redisAndDBTransactionUtil;

    @Transactional
    @Override
    public BaseResponse<JSONObject> login(@Valid UserLoginInpDTO userLoginInpDTO) throws Exception {
        //1.校验登陆类型
        Integer loginType = userLoginInpDTO.getLoginType();
        if(loginType<1 || loginType>loginTypeMax){
            return getResultError("登陆类型错误！");
        }

        //2.校验账号密码是否正确
        String oldPassword = userLoginInpDTO.getPassword();
        String newPassword = DigestUtils.md5DigestAsHex(oldPassword.getBytes());
        UserDO userDo = userDao.findByPhoneAndPassword(userLoginInpDTO.getPhone(), newPassword);
        if(userDo==null){
           return getResultError("账号或密码错误！");
        }
        /*
         * 3.将同类型的token失效
         */
        //查询同类型的有效token记录
        UserTokenDO oldUserTokenDo = userTokenDao.findByUserIdAndLoginTypeAndStatus(userDo.getUserId(),
                userLoginInpDTO.getLoginType(), MemberConst.MEMBER_LOGIN_TOKEN_VALID);
        //开启事务 防止出现异常时redis和db数据不一致
        TransactionStatus transactionStatus = redisAndDBTransactionUtil.begin();
        try {
            if(oldUserTokenDo!=null) {
                //移除redis的旧token
                tokenUtil.removeToken(oldUserTokenDo.getToken());
                //将数据库的token失效
                oldUserTokenDo.setStatus(MemberConst.MEMBER_LOGIN_TOKEN_INVALID);
                userTokenDao.save(oldUserTokenDo);
            }
            //4.生成token
            String[] loginTypeArray = loginTypeValue.split(",");
            String loginTypeStr = loginTypeArray[loginType-1];
            String key = loginTypeStr+"."+MemberConst.MEMBER_LOGIN_TOKEN_PREFIX;
            String token = tokenUtil.createToken(
                    key, userDo.getUserId() + "", MemberConst.MEMBER_LOGIN_TOKEN_TIMEOUT);
            //5.dto转do
            UserTokenDO userTokenDO = DtoUtil.dtoToDo(userLoginInpDTO, UserTokenDO.class);
            userTokenDO.setToken(token);
            userTokenDO.setStatus(MemberConst.MEMBER_LOGIN_TOKEN_VALID);
            userTokenDO.setUserId(userDo.getUserId());
            //6.保存token记录
            userTokenDao.save(userTokenDO);

            redisAndDBTransactionUtil.commit(transactionStatus);
            //7.返回数据
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("token",token);
            return getResultSuccess(jsonObject);
        }catch (Exception e){
            redisAndDBTransactionUtil.rollback(transactionStatus);
        }
        return getResultError("登陆失败！");
    }
}
