package com.lframework.starter.web.service;

import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;

import java.util.Map;

public interface IAliSmsService extends BaseService{

    /**
     * 发送短信
     * @param phoneNumbers 接收短信的手机号码，如果是多个支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
     * @param signName 短信签名名称
     * @param templateCode 短信模板Code
     */
    SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode);

    /**
     * 发送短信
     * @param phoneNumbers 接收短信的手机号码，如果是多个支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
     * @param signName 短信签名名称
     * @param templateCode 短信模板Code
     * @param templateParam 短信模板变量对应的实际值
     */
    SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode, Map<String, Object> templateParam);

    /**
     * 发送短信
     * @param phoneNumbers 接收短信的手机号码，如果是多个支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
     * @param signName 短信签名名称
     * @param templateCode 短信模板Code
     * @param templateParam 短信模板变量对应的实际值
     * @param smsUpExtendCode 上行短信扩展码，上行短信，指发送给通信服务提供商的短信，用于定制某种服务、完成查询，或是办理某种业务等，需要收费的，按运营商普通短信资费进行扣费。
     */
    SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode, Map<String, Object> templateParam, String smsUpExtendCode);

    /**
     * 发送短信
     * @param phoneNumbers 接收短信的手机号码，如果是多个支持对多个手机号码发送短信，手机号码之间以半角逗号（,）分隔。上限为1000个手机号码。批量调用相对于单条调用及时性稍有延迟。
     * @param signName 短信签名名称
     * @param templateCode 短信模板Code
     * @param templateParam 短信模板变量对应的实际值
     * @param smsUpExtendCode 上行短信扩展码，上行短信，指发送给通信服务提供商的短信，用于定制某种服务、完成查询，或是办理某种业务等，需要收费的，按运营商普通短信资费进行扣费。
     * @param outId 外部流水扩展字段
     */
    SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode, Map<String, Object> templateParam, String smsUpExtendCode, String outId);
}
