package com.liazhan.member;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @version V1.0
 * @description: 会员服务入口类
 * @author: Liazhan
 * @date: 2020/4/8 0:13
 */
@SpringBootApplication
@EnableFeignClients
@EnableSwagger2Doc
public class AppMember {

    public static void main(String[] args) {
        SpringApplication.run(AppMember.class,args);
    }
}
