package com.liazhan.weixin.service;

import com.liazhan.base.BaseResponse;
import com.liazhan.weixin.entity.TestEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @version V1.0
 * @description: 微信服务接口
 * @author: Liazhan
 * @date: 2020/4/7 21:59
 */
@Api(tags = "微信服务接口")
public interface WeiXinService {
    /**
     * 测试接口
     * @return
     */
    @ApiOperation(value = "测试接口")
    @GetMapping("/test")
    public BaseResponse<TestEntity> test();
}
