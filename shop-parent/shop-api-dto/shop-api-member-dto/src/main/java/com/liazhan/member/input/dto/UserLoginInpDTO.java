package com.liazhan.member.input.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @version:V1.0
 * @Description:
 * @author: Liazhan
 * @date 2020/4/27 15:37
 */
@Data
@ApiModel(value = "用户登录输入实体类")
public class UserLoginInpDTO {

    @NotBlank(message = "请输入手机号！")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机号格式错误！")
    @ApiModelProperty(value = "手机号码")
    private String phone;

    @NotBlank(message = "请输入密码！")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotNull(message = "登陆类型为空！")
    @ApiModelProperty(value = "登录类型 1=pc,2=android,3=ios")
    private Integer loginType;

    @NotBlank(message = "请输入设备信息！")
    @ApiModelProperty(value = "设备信息")
    private String deviceInfor;
}
