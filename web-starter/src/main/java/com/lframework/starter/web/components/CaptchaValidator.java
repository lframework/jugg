package com.lframework.starter.web.components;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.components.redis.RedisHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 验证码校验
 */
@Slf4j
public class CaptchaValidator {

  @Autowired
  private RedisHandler redisHandler;

  /**
   * 校验验证码 如果未通过校验则会抛出异常
   *
   * @param sn
   * @param captcha
   */
  public void validate(String sn, String captcha) {

    log.debug("开始校验验证码，sn={}, captcha={}", sn, captcha);

    if (StringUtil.isEmpty(sn) || StringUtil.isEmpty(captcha)) {
      log.debug("sn、captcha为空，校验不通过");
      throw new DefaultClientException("验证码错误，请重新输入！");
    }

    String captchaKey = StringUtil.format(StringPool.LOGIN_CAPTCHA_KEY, sn);
    try {
      String oriCaptcha = (String) redisHandler.get(captchaKey);
      log.debug("从redis中取出正确的captcha={}", oriCaptcha);

      if (StringUtil.isEmpty(oriCaptcha)) {
        log.debug("验证码已过期");
        throw new DefaultClientException("验证码已过期，请重新输入！");
      }

      if (!StringUtil.equalsIgnoreCase(oriCaptcha, captcha)) {
        log.debug("验证码错误");
        throw new DefaultClientException("验证码错误，请重新输入！");
      }
    } finally {
      // 无论校验是否通过，都需要重新生成验证码
      redisHandler.del(captchaKey);
    }
  }
}
