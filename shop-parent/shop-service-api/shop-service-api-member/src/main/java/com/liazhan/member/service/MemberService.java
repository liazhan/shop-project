package com.liazhan.member.service;

import com.liazhan.weixin.entity.TestEntity;
import org.springframework.web.bind.annotation.GetMapping;

/** 会员服务接口
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/7 23:54
 */
public interface MemberService {

    /**
     * 调用微信服务测试接口
     * @return
     */
    @GetMapping("/callWeiXin")
    public TestEntity callWeiXin();
}
