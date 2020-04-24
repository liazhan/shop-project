package com.liazhan.weixin.feign;

import com.liazhan.member.service.MemberService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @version:V1.0
 * @Description: 会员服务feign调用
 * @author: Liazhan
 * @date 2020/4/22 16:51
 */
@FeignClient(name = "liazhan-member")
public interface MemberServiceFeign extends MemberService {

}
