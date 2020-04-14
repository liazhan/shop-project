package com.liazhan.member.service.impl;

import com.liazhan.base.BaseResponse;
import com.liazhan.base.consts.BaseConst;
import com.liazhan.member.feign.WeiXinServiceFeign;
import com.liazhan.member.service.MemberService;
import com.liazhan.weixin.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version V1.0
 * @description: 会员服务接口实现类
 * @author: Liazhan
 * @date: 2020/4/8 0:08
 */
@RestController
@RefreshScope
public class MemberServiceImpl implements MemberService {
    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;

    @Value("${spring.application.name}")
    private String name;

    @Override
    public BaseResponse<TestEntity> callWeiXin() {
        BaseResponse<TestEntity> testResponse = weiXinServiceFeign.test();
        testResponse.getData().setName(name);
        return testResponse;
    }
}
