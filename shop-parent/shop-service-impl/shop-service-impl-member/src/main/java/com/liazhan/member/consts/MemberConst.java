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
    //七牛云accessKey
    String QINIU_ACCESSKEY = "这里输入七牛云accessKey";
    //七牛云secretKey
    String QINIU_SECRETKEY = "这里输入七牛云secretKey";
    //七牛云头像存储空间名称
    String QINIU_HEADIMG_BUCKET = "这里输入七牛云存储空间名称";
    //用户获取上传头像token的次数在redis的key前缀
    String HEADIMG_TOKEN_COUNT_PREFIX = "headimg.token.count";
    //用户获取上传头像token的次数在redis的过期时间  半小时
    Long HEADIMG_TOKEN_COUNT_TIMEOUT = 1800L;
    //用户获取上传头像token的最大次数   10次
    Integer HEADIMG_TOKEN_COUNT_MAX = 10;
}
