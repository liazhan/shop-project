package com.liazhan.member.service;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.member.input.dto.UserLoginInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @version:V1.0
 * @Description: 会员登录相关接口
 * @author: Liazhan
 * @date 2020/4/27 16:33
 */
@Api(tags = "会员登录相关接口")
public interface MemberLoginService {

    @PostMapping("/login")
    @ApiOperation(value = "登录接口")
    BaseResponse<JSONObject> login(@RequestBody @Valid UserLoginInpDTO userLoginInpDTO) throws Exception;
}
