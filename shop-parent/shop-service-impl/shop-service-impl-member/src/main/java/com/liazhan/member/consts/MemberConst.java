package com.liazhan.member.consts;

/**
 * @version:V1.0
 * @Description: 会员服务常量类
 * @author: Liazhan
 * @date 2020/4/28 10:11
 */
public interface MemberConst {
    //登录token在redis的key前缀
    String MEMBER_LOGIN_TOKEN_PREFIX = "login.token";
    //登录token过期时间  1小时
    Long MEMBER_LOGIN_TOKEN_TIMEOUT = 3600L;
    //登陆token失效状态
    Integer MEMBER_LOGIN_TOKEN_INVALID = 0;
    //登陆token有效状态
    Integer MEMBER_LOGIN_TOKEN_VALID = 1;
}
