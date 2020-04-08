package com.liazhan.weixin.service.impl;

import com.liazhan.weixin.entity.TestEntity;
import com.liazhan.weixin.service.WeiXinService;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/7 23:05
 */
@RestController
public class WeiXinServiceImpl implements WeiXinService {
    @Override
    public TestEntity test() {
        return new TestEntity("Liazhan","GuangZhou");
    }
}
