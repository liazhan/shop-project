package com.liazhan.core.utils;

import org.springframework.beans.BeanUtils;

/**
 * @version:V1.0
 * @Description: DTO工具类
 * @author: Liazhan
 * @date 2020/4/21 17:00
 */
public class DtoUtil<Dto,Do>{

    /**
     * dto转do
     */
    public static <Do> Do dtoToDo(Object dtoEntity, Class<Do> doClass) {
        // 判断dtoEntity是否为空!
        if (dtoEntity == null) {
            return null;
        }
        // 判断doClass是否为空
        if (doClass == null) {
            return null;
        }
        try {
            // dto转为do
            Do newInstance = doClass.newInstance();
            BeanUtils.copyProperties(dtoEntity, newInstance);
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * do转dto
     */
    public static <Dto> Dto doToDto(Object doEntity, Class<Dto> dtoClass) {
        // 判断doEntity是否为空!
        if (doEntity == null) {
            return null;
        }
        // 判断dtoClass是否为空
        if (dtoClass == null) {
            return null;
        }
        try {
            // do转为dto
            Dto newInstance = dtoClass.newInstance();
            BeanUtils.copyProperties(doEntity, newInstance);
            return newInstance;
        } catch (Exception e) {
            return null;
        }
    }
}
