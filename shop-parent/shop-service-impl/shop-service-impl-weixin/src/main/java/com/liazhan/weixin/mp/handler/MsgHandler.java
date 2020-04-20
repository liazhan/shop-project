package com.liazhan.weixin.mp.handler;

import com.liazhan.weixin.mp.builder.TextBuilder;
import com.liazhan.weixin.sms.SendSms;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        //向公众号发送手机号
        String content = wxMessage.getContent();
        //向手机号发送短信
        String code = SendSms.send(content);
        //返回提示
        content = "已发送验证码，请注意查收！";
        return new TextBuilder().build(content, wxMessage, weixinService);

    }

}
