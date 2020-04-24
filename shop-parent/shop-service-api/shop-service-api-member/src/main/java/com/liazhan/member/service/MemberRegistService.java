package com.liazhan.member.service;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.member.input.dto.UserRegistInpDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

/**
 * @version:V1.0
 * @Description: 会员注册相关接口
 * @author: Liazhan
 * @date 2020/4/24 11:13
 */
@Api(tags = "会员注册相关接口")
public interface MemberRegistService {

    @PostMapping("/regist")
    @ApiOperation(value = "会员注册接口")
    BaseResponse<JSONObject> regist(@RequestBody @Valid UserRegistInpDTO userInpDTO);
}
