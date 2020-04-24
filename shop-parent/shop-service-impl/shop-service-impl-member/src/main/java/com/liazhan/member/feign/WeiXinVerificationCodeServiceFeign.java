package com.liazhan.member.feign;

import com.liazhan.weixin.service.VerificationCodeService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @version:V1.0
 * @Description: 微信服务验证码接口Feign调用
 * @author: Liazhan
 * @date 2020/4/24 14:42
 */
@FeignClient(name = "liazhan-weixin")
public interface WeiXinVerificationCodeServiceFeign extends VerificationCodeService {

}
