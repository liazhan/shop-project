package com.liazhan.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.BaseServiceImpl;
import com.liazhan.base.consts.BaseConst;
import com.liazhan.core.utils.DtoUtil;
import com.liazhan.member.dao.UserDao;
import com.liazhan.member.dao.entity.UserDO;
import com.liazhan.member.feign.WeiXinVerificationCodeServiceFeign;
import com.liazhan.member.input.dto.UserRegistInpDTO;
import com.liazhan.member.service.MemberRegistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/**
 * @version:V1.0
 * @Description: 会员注册相关接口实现类
 * @author: Liazhan
 * @date 2020/4/24 11:18
 */
@RestController
public class MemberRegistServiceImpl extends BaseServiceImpl<JSONObject> implements MemberRegistService {
    @Autowired
    private WeiXinVerificationCodeServiceFeign weiXinVerificationCodeServiceFeign;
    @Autowired
    private UserDao userDao;

    @Override
    public BaseResponse<JSONObject> regist(@Valid UserRegistInpDTO userInpDTO) {
        //1.校验验证码是否正确
        BaseResponse<JSONObject> checkResponse = weiXinVerificationCodeServiceFeign.checkVerificationCode(userInpDTO.getPhone(), userInpDTO.getVerificationCode());
        if(!BaseConst.HTTP_RES_CODE_200.equals(checkResponse.getCode())){
            return getResultError(checkResponse.getMsg());
        }
        //2.密码用md5加密
        String encodePassword = DigestUtils.md5DigestAsHex(userInpDTO.getPassword().getBytes());
        userInpDTO.setPassword(encodePassword);
        //3.DTO转do
        UserDO userDO = DtoUtil.dtoToDo(userInpDTO, UserDO.class);
        //4.保存到数据库
        UserDO save = userDao.save(userDO);
        return save.getUserId()==null?getResultError("注册失败！"):getResultSuccess("注册成功！");
    }
}
