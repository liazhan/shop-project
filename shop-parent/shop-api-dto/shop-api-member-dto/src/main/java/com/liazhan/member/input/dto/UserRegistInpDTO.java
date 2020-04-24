package com.liazhan.member.input.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;

/**
 * @version:V1.0
 * @Description: 用户注册输入实体类
 * @author: Liazhan
 * @date 2020/4/21 16:23
 */
@Data
@ApiModel(value = "用户注册输入实体类")
public class UserRegistInpDTO {

    @NotBlank(message = "请输入用户名称！")
    @Size(min = 1,max = 11,message = "用户名称长度必须是1-11个字符")
    @ApiModelProperty(value = "用户名称")
    private String userName;

    @NotBlank(message = "请输入手机号！")
    @Pattern(regexp = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$",message = "手机号格式错误！")
    @ApiModelProperty(value = "用户手机号")
    private String phone;

    @NotBlank(message = "请输入验证码！")
    @ApiModelProperty(value = "验证码")
    private String verificationCode;

    @NotBlank(message = "请输入密码！")
    @Size(min = 6,max = 16,message = "密码长度必须是6-16个字符")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "请输入邮箱号！")
    @Email(message = "邮箱格式不正确")
    @ApiModelProperty(value = "用户邮箱号")
    private String email;

    @NotNull(message = "请选择性别！")
    @Range(min = 0,max = 1)
    @ApiModelProperty(value = "用户性别")
    private Integer sex;

    @NotNull(message = "请输入年龄！")
    @ApiModelProperty(value = "用户年龄")
    private Integer age;

    @NotBlank(message = "请上传头像！")
    @ApiModelProperty(value = "用户头像")
    private String picUrl;

}
