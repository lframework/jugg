package com.lframework.starter.web.config.properties;

import com.google.code.kaptcha.Constants;
import java.util.Properties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 验证码配置信息
 *
 * @author zmj
 */
@ConfigurationProperties(prefix = "jugg.kaptcha")
public class KaptchaProperties {

  private static final int DEFAULT_EXPIRE_TIME = 5;

  private Integer expireTime = DEFAULT_EXPIRE_TIME;

  private String sessionConfigKey = Constants.KAPTCHA_SESSION_KEY;

  private String sessionConfigDate = Constants.KAPTCHA_SESSION_DATE;

  private Boolean border = true;

  private String borderColor = "black";

  private String borderThickness = "1";

  private String noiseColor = "black";

  private String noiseImpl = "com.google.code.kaptcha.impl.DefaultNoise";

  private String obscurificatorImpl = "com.google.code.kaptcha.impl.WaterRipple";

  private String producerImpl = "com.google.code.kaptcha.impl.DefaultKaptcha";

  private String textProducerImpl = "com.google.code.kaptcha.text.impl.DefaultTextCreator";

  private String textProducerCharString = "abcde2345678gfynmnpwx";

  private String textProducerCharLength = "5";

  private String textProducerFontNames = "Arial,Courier";

  private String textProducerFontColor = "black";

  private String textProducerFontSize = "40";

  private String textProducerCharSpace = "2";

  private String wordRenderImpl = "com.google.code.kaptcha.text.impl.DefaultWordRenderer";

  private String backGroundImpl = "com.google.code.kaptcha.impl.DefaultBackground";

  private String backGroundClrFrom = "lightGray";

  private String backGroundClrTo = "white";

  private String imageWidth = "200";

  private String imageHeight = "50";

  public Properties props() {

    Properties properties = new Properties();
    properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, this.getSessionConfigKey());
    properties.put(Constants.KAPTCHA_SESSION_CONFIG_DATE, this.getSessionConfigDate());
    properties.put(Constants.KAPTCHA_BORDER, this.getBorder());
    properties.put(Constants.KAPTCHA_BORDER_COLOR, this.getBorderColor());
    properties.put(Constants.KAPTCHA_BORDER_THICKNESS, this.getBorderThickness());
    properties.put(Constants.KAPTCHA_NOISE_COLOR, this.getNoiseColor());
    properties.put(Constants.KAPTCHA_NOISE_IMPL, this.getNoiseImpl());
    properties.put(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, this.getObscurificatorImpl());
    properties.put(Constants.KAPTCHA_PRODUCER_IMPL, this.getProducerImpl());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_IMPL, this.getTextProducerImpl());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, this.getTextProducerCharString());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, this.getTextProducerCharLength());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, this.getTextProducerFontNames());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, this.getTextProducerFontColor());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, this.getTextProducerFontSize());
    properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_SPACE, this.getTextProducerCharSpace());
    properties.put(Constants.KAPTCHA_WORDRENDERER_IMPL, this.getWordRenderImpl());
    properties.put(Constants.KAPTCHA_BACKGROUND_IMPL, this.getBackGroundImpl());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_FROM, this.getBackGroundClrFrom());
    properties.put(Constants.KAPTCHA_BACKGROUND_CLR_TO, this.getBackGroundClrTo());
    properties.put(Constants.KAPTCHA_IMAGE_WIDTH, this.getImageWidth());
    properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, this.getImageHeight());

    return properties;
  }

  public Integer getExpireTime() {

    return expireTime;
  }

  public void setExpireTime(Integer expireTime) {

    if (expireTime < 0) {
      expireTime = DEFAULT_EXPIRE_TIME;
    }
    this.expireTime = expireTime;
  }

  public String getSessionConfigKey() {

    return sessionConfigKey;
  }

  public void setSessionConfigKey(String sessionConfigKey) {

    this.sessionConfigKey = sessionConfigKey;
  }

  public String getSessionConfigDate() {

    return sessionConfigDate;
  }

  public void setSessionConfigDate(String sessionConfigDate) {

    this.sessionConfigDate = sessionConfigDate;
  }

  public Boolean getBorder() {

    return border;
  }

  public void setBorder(Boolean border) {

    this.border = border;
  }

  public String getBorderColor() {

    return borderColor;
  }

  public void setBorderColor(String borderColor) {

    this.borderColor = borderColor;
  }

  public String getBorderThickness() {

    return borderThickness;
  }

  public void setBorderThickness(String borderThickness) {

    this.borderThickness = borderThickness;
  }

  public String getNoiseColor() {

    return noiseColor;
  }

  public void setNoiseColor(String noiseColor) {

    this.noiseColor = noiseColor;
  }

  public String getNoiseImpl() {

    return noiseImpl;
  }

  public void setNoiseImpl(String noiseImpl) {

    this.noiseImpl = noiseImpl;
  }

  public String getObscurificatorImpl() {

    return obscurificatorImpl;
  }

  public void setObscurificatorImpl(String obscurificatorImpl) {

    this.obscurificatorImpl = obscurificatorImpl;
  }

  public String getProducerImpl() {

    return producerImpl;
  }

  public void setProducerImpl(String producerImpl) {

    this.producerImpl = producerImpl;
  }

  public String getTextProducerImpl() {

    return textProducerImpl;
  }

  public void setTextProducerImpl(String textProducerImpl) {

    this.textProducerImpl = textProducerImpl;
  }

  public String getTextProducerCharString() {

    return textProducerCharString;
  }

  public void setTextProducerCharString(String textProducerCharString) {

    this.textProducerCharString = textProducerCharString;
  }

  public String getTextProducerCharLength() {

    return textProducerCharLength;
  }

  public void setTextProducerCharLength(String textProducerCharLength) {

    this.textProducerCharLength = textProducerCharLength;
  }

  public String getTextProducerFontNames() {

    return textProducerFontNames;
  }

  public void setTextProducerFontNames(String textProducerFontNames) {

    this.textProducerFontNames = textProducerFontNames;
  }

  public String getTextProducerFontColor() {

    return textProducerFontColor;
  }

  public void setTextProducerFontColor(String textProducerFontColor) {

    this.textProducerFontColor = textProducerFontColor;
  }

  public String getTextProducerFontSize() {

    return textProducerFontSize;
  }

  public void setTextProducerFontSize(String textProducerFontSize) {

    this.textProducerFontSize = textProducerFontSize;
  }

  public String getTextProducerCharSpace() {

    return textProducerCharSpace;
  }

  public void setTextProducerCharSpace(String textProducerCharSpace) {

    this.textProducerCharSpace = textProducerCharSpace;
  }

  public String getWordRenderImpl() {

    return wordRenderImpl;
  }

  public void setWordRenderImpl(String wordRenderImpl) {

    this.wordRenderImpl = wordRenderImpl;
  }

  public String getBackGroundImpl() {

    return backGroundImpl;
  }

  public void setBackGroundImpl(String backGroundImpl) {

    this.backGroundImpl = backGroundImpl;
  }

  public String getBackGroundClrFrom() {

    return backGroundClrFrom;
  }

  public void setBackGroundClrFrom(String backGroundClrFrom) {

    this.backGroundClrFrom = backGroundClrFrom;
  }

  public String getBackGroundClrTo() {

    return backGroundClrTo;
  }

  public void setBackGroundClrTo(String backGroundClrTo) {

    this.backGroundClrTo = backGroundClrTo;
  }

  public String getImageWidth() {

    return imageWidth;
  }

  public void setImageWidth(String imageWidth) {

    this.imageWidth = imageWidth;
  }

  public String getImageHeight() {

    return imageHeight;
  }

  public void setImageHeight(String imageHeight) {

    this.imageHeight = imageHeight;
  }
}
