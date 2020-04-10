package com.liazhan.weixin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @version V1.0
 * @description: 测试用实体类
 * @author: Liazhan
 * @date: 2020/4/7 21:55
 */

@ApiModel(value = "测试实体类")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestEntity {
    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名")
    String name;
    /**
     * 地址
     */
    @ApiModelProperty(value = "地址")
    String address;
}
