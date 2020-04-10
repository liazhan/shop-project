package com.liazhan.weixin;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @version V1.0
 * @description: 微信服务入口类
 * @author: Liazhan
 * @date: 2020/4/8 0:14
 */
@SpringBootApplication
@EnableSwagger2Doc
public class AppWeiXin {

    public static void main(String[] args) {
        SpringApplication.run(AppWeiXin.class,args);
    }
}
