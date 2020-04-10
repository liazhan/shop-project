package com.liazhan.member.service;

import com.liazhan.weixin.entity.TestEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @version V1.0
 * @description: 会员服务接口
 * @author: Liazhan
 * @date: 2020/4/7 23:54
 */
@Api(tags = "会员服务接口")
public interface MemberService {

    /**
     * 调用微信服务测试接口
     * @return
     */
    @ApiOperation(value = "调用微信服务测试接口")
    @GetMapping("/callWeiXin")
    public TestEntity callWeiXin();
}
