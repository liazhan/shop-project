package com.liazhan.weixin.consts;

/**
 * @version:V1.0
 * @Description: 公众号相关常量类
 * @author: Liazhan
 * @date 2020/4/22 17:13
 */
public interface MPConst {
    //注册验证码存放的key前缀
    String REGIST_CODE_KEY_PREFIX = "regist.code";
    //注册验证码存放的key过期时间 10分钟
    Long REGIST_CODE_KEY_TIMEOUT = 600L;
}
