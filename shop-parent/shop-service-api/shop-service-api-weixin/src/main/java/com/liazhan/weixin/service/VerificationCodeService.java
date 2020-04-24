package com.liazhan.weixin.service;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version:V1.0
 * @Description: 微信服务验证码接口
 * @author: Liazhan
 * @date 2020/4/23 16:43
 */
@Api(tags = "微信服务验证码接口")
public interface VerificationCodeService {

    @ApiOperation(value = "根据手机号码和验证码校验验证码是否正确")
    @GetMapping("/checkVerificationCode")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query",name = "phone",dataType = "String",required = true,value = "手机号码"),
            @ApiImplicitParam(paramType = "query",name = "verificationCode",dataType = "String",required = true,value = "验证码")
    })
    public BaseResponse<JSONObject> checkVerificationCode(
            @RequestParam("phone") String phone,
            @RequestParam("verificationCode") String verificationCode);
}
