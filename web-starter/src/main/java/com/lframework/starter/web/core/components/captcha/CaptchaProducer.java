package com.lframework.starter.web.core.components.captcha;

import java.awt.image.BufferedImage;

public interface CaptchaProducer {

  String createText();

  BufferedImage createImage(String text);
}
