package com.liazhan.weixin.service;

import com.liazhan.weixin.entity.TestEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**微信服务接口
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/7 21:59
 */
public interface WeiXinService {
    /**
     * 测试接口
     * @return
     */
    @GetMapping("/test")
    public TestEntity test();
}
