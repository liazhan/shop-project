package com.liazhan.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version:V1.0
 * @Description: 微服务接口统一响应体
 * @author: Liazhan
 * @date 2020/4/14 14:44
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    //响应码
    private Integer code;
    //响应消息
    private String msg;
    //响应数据
    private T data;
}
