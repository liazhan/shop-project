package com.liazhan.member.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.BaseServiceImpl;
import com.liazhan.base.consts.BaseConst;
import com.liazhan.core.utils.RegexUtil;
import com.liazhan.member.dao.UserDao;
import com.liazhan.member.service.MemberService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @description: 会员服务接口实现类
 * @author: Liazhan
 * @date: 2020/4/8 0:08
 */
@RestController
public class MemberServiceImpl extends BaseServiceImpl<JSONObject> implements MemberService {
    @Autowired
    private UserDao userDao;

    @Override
    public BaseResponse<JSONObject> existsByPhone(String phone) {
        //1.校验参数
        if(StringUtils.isBlank(phone)){
            return getResultError("手机号码不能为空！");
        }
        if(!RegexUtil.checkMobile(phone)){
            return getResultError("手机号码错误！");
        }
        //2.根据手机号查询用户是否存在
        boolean isExists = userDao.existsByPhone(phone);
        if(!isExists){
            return getRusult(BaseConst.HTTP_RES_CODE_201,"用户不存在！",null);
        }
        return getResultSuccess("用户存在！");
    }
}
