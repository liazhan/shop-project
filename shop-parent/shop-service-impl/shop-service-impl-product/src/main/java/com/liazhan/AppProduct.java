package com.liazhan;
 
import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
 
/**
 * @version V1.0
 * @description: 商品服务启动类
 * @author: Liazhan
 * @date: 2020/5/25 22:33
 */
@SpringBootApplication
@EnableSwagger2Doc
public class AppProduct {
    public static void main(String[] args) {
        SpringApplication.run(AppProduct.class,args);
    }
}