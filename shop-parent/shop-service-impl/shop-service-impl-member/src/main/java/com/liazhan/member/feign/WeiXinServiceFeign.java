package com.liazhan.member.feign;

import com.liazhan.weixin.service.WeiXinService;
import org.springframework.cloud.openfeign.FeignClient;

/** 微信服务feign调用
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/8 0:01
 */
@FeignClient(name = "liazhan-weixin")
public interface WeiXinServiceFeign extends WeiXinService {

}
