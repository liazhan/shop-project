package com.liazhan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @version:V1.0
 * @Description: 配置中心入口类
 * @author: Liazhan
 * @date 2020/4/13 15:50
 */
@SpringBootApplication
@EnableConfigServer
public class AppConfig {
    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class,args);
    }
}
