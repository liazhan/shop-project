package com.liazhan.member.service.impl;

import com.liazhan.member.feign.WeiXinServiceFeign;
import com.liazhan.member.service.MemberService;
import com.liazhan.weixin.entity.TestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**会员服务接口实现类
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/8 0:08
 */
@RestController
public class MemberServiceImpl implements MemberService {
    @Autowired
    private WeiXinServiceFeign weiXinServiceFeign;

    @Override
    public TestEntity callWeiXin() {
        return weiXinServiceFeign.test();
    }
}
