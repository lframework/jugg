package com.lframework.starter.web.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.dysmsapi20170525.models.SendSmsResponse;
import com.aliyun.dysmsapi20170525.models.SendSmsResponseBody;
import com.lframework.common.exceptions.ClientException;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.service.IAliSmsService;
import com.lframework.starter.web.utils.JsonUtil;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AliSmsServiceImpl implements IAliSmsService {

  private Client client;

  public AliSmsServiceImpl(Client client) {
    this.client = client;
  }

  @Override
  public SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode) {
    return send(phoneNumbers, signName, templateCode, null, null, null);
  }

  @Override
  public SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode,
      Map<String, Object> templateParam) {
    return send(phoneNumbers, signName, templateCode, templateParam, null, null);
  }

  @Override
  public SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode,
      Map<String, Object> templateParam, String smsUpExtendCode) {
    return send(phoneNumbers, signName, templateCode, templateParam, smsUpExtendCode, null);
  }

  @Override
  public SendSmsResponseBody send(String phoneNumbers, String signName, String templateCode,
      Map<String, Object> templateParam, String smsUpExtendCode, String outId) {
    SendSmsRequest sendSmsRequest = new SendSmsRequest()
        .setPhoneNumbers(phoneNumbers)
        .setSignName(signName)
        .setTemplateCode(templateCode)
        .setTemplateParam(
            CollectionUtil.isEmpty(templateParam) ? null : JsonUtil.toJsonString(templateParam))
        .setSmsUpExtendCode(smsUpExtendCode)
        .setOutId(outId);

    if (log.isDebugEnabled()) {
      log.debug(
          "【阿里短信】准备发送短信, phoneNumbers={}, signName={}, templateCode={}, templateParams={}, smsUpExtendCode={}, outId={}",
          phoneNumbers, signName, templateCode, templateParam, smsUpExtendCode, outId);
    }

    SendSmsResponse resp;
    try {
      log.info("【阿里短信】开始调用短信发送接口");
      resp = this.client.sendSms(sendSmsRequest);
      SendSmsResponseBody body = resp.getBody();
      if ("OK".equals(body.getCode())) {
        // 调用成功
        log.info("【阿里短信】调用短信发送接口成功");
        return body;
      }

      log.error("【阿里短信】短信发送接口调用失败，具体原因={}", body.getMessage());
      throw new DefaultClientException(body.getMessage());
    } catch (Exception e) {
      if (e instanceof ClientException) {
        throw (ClientException) e;
      }

      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }
}
