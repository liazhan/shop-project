package com.liazhan.core.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @version:V1.0
 * @Description: Redis工具类
 * @author: Liazhan
 * @date 2020/4/22 15:56
 */
@Component
public class RedisUtil {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 存放String类型,有过期时间
     * @param key
     * @param data
     * @param timeout 过期时间,单位为秒
     */
    public void setString(String key,String data,Long timeout){
        stringRedisTemplate.opsForValue().set(key,data);
        if(timeout!=null){
            stringRedisTemplate.expire(key,timeout,TimeUnit.SECONDS);
        }
    }

    /**
     * 存放String类型
     * @param key
     * @param data
     */
    public void setString(String key,String data){
        stringRedisTemplate.opsForValue().set(key,data);
    }

    /**
     * 根据key获取String类型数据
     * @param key
     * @return String
     */
    public String getString(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    /**
     * 根据key删除
     * @param key
     * @return Boolean
     */
    public Boolean delKey(String key){
        return stringRedisTemplate.delete(key);
    }
}
