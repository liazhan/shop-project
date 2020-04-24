package com.liazhan.weixin.mp.handler;

import com.alibaba.fastjson.JSONObject;
import com.liazhan.base.BaseResponse;
import com.liazhan.base.consts.BaseConst;
import com.liazhan.core.utils.RedisUtil;
import com.liazhan.core.utils.RegexUtil;
import com.liazhan.weixin.consts.MPConst;
import com.liazhan.weixin.feign.MemberServiceFeign;
import com.liazhan.weixin.mp.builder.TextBuilder;
import com.liazhan.weixin.sms.SendSms;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@RefreshScope
public class MsgHandler extends AbstractHandler {
    //发送验证码短信后的提示回复
    @Value("${weixin.mp.regist.code.message}")
    private String registCodeMsg;
    //手机号已注册时的提示回复
    @Value("${weixin.mp.regist.exists.message}")
    private String existsMsg;
    //操作频繁时的提示回复
    @Value("${weixin.mp.regist.frequently.message}")
    private String frequentlyMsg;
    //默认的回复
    @Value("${weixin.mp.default.message}")
    private String defaultMsg;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private MemberServiceFeign memberServiceFeign;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        //1.公众号接收的消息
        String content = wxMessage.getContent();
        //2.校验消息是否是手机号,不是手机号直接回复默认消息
        if(!RegexUtil.checkMobile(content)){
            return new TextBuilder().build(defaultMsg, wxMessage, weixinService);
        }
        //3.校验手机号是否已注册
        BaseResponse<JSONObject> baseResponse = memberServiceFeign.existsByPhone(content);
        //已注册
        if(BaseConst.HTTP_RES_CODE_200.equals(baseResponse.getCode())){
            return new TextBuilder().build(existsMsg, wxMessage, weixinService);
        }
        //未注册
        if(BaseConst.HTTP_RES_CODE_201.equals(baseResponse.getCode())){
            //判断验证码是否已存在，若已存在，则提示操作频繁，稍后重试
            if(redisUtil.getString(MPConst.REGIST_CODE_KEY_PREFIX+content)!=null){
                return new TextBuilder().build(frequentlyMsg, wxMessage, weixinService);
            }
            //4.向手机号发送短信
            String code = SendSms.send(content);
            //5.将验证码存入redis,key为前缀+手机号
            redisUtil.setString(MPConst.REGIST_CODE_KEY_PREFIX+content,code,MPConst.REGIST_CODE_KEY_TIMEOUT);

        }else{  //接口错误
            return new TextBuilder().build(baseResponse.getMsg(), wxMessage, weixinService);
        }

        return new TextBuilder().build(registCodeMsg, wxMessage, weixinService);
    }

}
