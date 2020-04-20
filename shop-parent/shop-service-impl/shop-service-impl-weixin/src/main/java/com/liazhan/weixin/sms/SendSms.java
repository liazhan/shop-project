package com.liazhan.weixin.sms;

import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.sms.v20190711.SmsClient;
import com.tencentcloudapi.sms.v20190711.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20190711.models.SendSmsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version:V1.0
 * @Description: 腾讯短信发送类
 * @author: Liazhan
 * @date 2020/4/20 9:32
 */
public class SendSms {
    static Logger log = LoggerFactory.getLogger(SendSms.class);

    /**
     * @Author Liazhan
     * @Description //TODO  发送短信
     * @Date 11:46 2020/4/20
     * @Param [phone] 手机号
     * @return String 验证码
     **/
    public static String send(String phone) {
        try{
            Credential cred = new Credential("这里输入腾讯云secretId", "这里输入腾讯云secretKey");

            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint("sms.tencentcloudapi.com");

            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);

            SmsClient client = new SmsClient(cred, "", clientProfile);

            //生成4位随机数
            int code = (int) (Math.random() * 9000 + 1000);

            String params = "{\"PhoneNumberSet\":[\"+86"+phone+"\"],\"TemplateID\":\"这里输入短信模板id\",\"Sign\":\"这里输入短信签名内容\",\"TemplateParamSet\":[\""+code+"\"],\"SmsSdkAppid\":\"这里输入SmsSdkAppid\"}";
            SendSmsRequest req = SendSmsRequest.fromJsonString(params, SendSmsRequest.class);

            SendSmsResponse resp = client.SendSms(req);

            log.info(SendSmsRequest.toJsonString(resp));

            return String.valueOf(code);
        } catch (TencentCloudSDKException e) {
            log.error(e.getMessage());
        }
        return "";
    }
}
