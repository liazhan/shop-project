package com.liazhan.member.service;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @version V1.0
 * @description: 会员服务接口
 * @author: Liazhan
 * @date: 2020/4/7 23:54
 */
@Api(tags = "会员服务接口")
public interface MemberService {

    @ApiOperation(value = "根据手机号查询用户是否存在 code 500=接口错误，200=用户存在，201=用户不存在")
    @GetMapping("/existsByPhone")
    @ApiImplicitParam(paramType = "query",name = "phone",dataType = "String",required = true,value = "手机号码")
    public BaseResponse<JSONObject> existsByPhone(@RequestParam(value = "phone") String phone);
}
