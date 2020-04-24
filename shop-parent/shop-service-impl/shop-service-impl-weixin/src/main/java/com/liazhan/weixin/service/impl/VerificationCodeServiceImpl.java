package com.liazhan.weixin.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.BaseServiceImpl;
import com.liazhan.core.utils.RedisUtil;
import com.liazhan.core.utils.RegexUtil;
import com.liazhan.weixin.consts.MPConst;
import com.liazhan.weixin.service.VerificationCodeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version:V1.0
 * @Description: 微信服务验证码接口实现类
 * @author: Liazhan
 * @date 2020/4/23 16:56
 */
@RestController
public class VerificationCodeServiceImpl extends BaseServiceImpl<JSONObject> implements VerificationCodeService {
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> checkVerificationCode(String phone, String verificationCode) {
        //1.校验参数
        if(StringUtils.isBlank(phone)){
            return getResultError("手机号码不能为空！");
        }
        if(!RegexUtil.checkMobile(phone)){
            return getResultError("手机号码错误！");
        }
        if(StringUtils.isBlank(verificationCode)){
            return getResultError("验证码不能为空！");
        }
        //2.从redis取出验证码
        String codeKey = MPConst.REGIST_CODE_KEY_PREFIX + phone;
        String code = redisUtil.getString(codeKey);
        //3.比对验证码是否一致
        if(code==null){
            return getResultError("验证码可能已经过期！");
        }
        if(!verificationCode.equals(code)){
            return getResultError("验证码错误！");
        }
        //4.移除redis验证码
        redisUtil.delKey(codeKey);
        return getResultSuccess("验证码正确！");
    }
}
