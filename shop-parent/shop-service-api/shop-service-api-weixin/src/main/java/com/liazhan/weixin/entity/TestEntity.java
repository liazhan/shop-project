package com.liazhan.weixin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** 测试用实体类
 * @version V1.0
 * @description:
 * @author: Liazhan
 * @date: 2020/4/7 21:55
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    /**
     * 姓名
     */
    String name;
    /**
     * 地址
     */
    String address;
}
