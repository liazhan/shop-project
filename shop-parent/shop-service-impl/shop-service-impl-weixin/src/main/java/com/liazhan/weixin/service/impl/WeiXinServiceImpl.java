package com.liazhan.weixin.service.impl;

import com.liazhan.weixin.entity.TestEntity;
import com.liazhan.weixin.service.WeiXinService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @description: 微信服务接口实现类
 * @author: Liazhan
 * @date: 2020/4/7 23:05
 */
@RestController
@RefreshScope
public class WeiXinServiceImpl implements WeiXinService {
    @Value("${spring.application.name}")
    private String name;

    @Override
    public TestEntity test() {
        return new TestEntity(name,"GuangZhou");
    }
}
