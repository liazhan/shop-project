package com.liazhan.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**会员服务入口类
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/8 0:13
 */
@SpringBootApplication
@EnableFeignClients
public class AppMember {

    public static void main(String[] args) {
        SpringApplication.run(AppMember.class,args);
    }
}
