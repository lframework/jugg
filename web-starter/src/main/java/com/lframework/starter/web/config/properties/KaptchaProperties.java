package com.lframework.starter.web.config.properties;

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

  private Boolean border = true;

  private String borderColor = "black";

  private String borderThickness = "1";

  private String noiseColor = "black";

  private String textProducerCharString = "abcde2345678gfynmnpwx";

  private String textProducerCharLength = "5";

  private String textProducerFontNames = "Arial,Courier";

  private String textProducerFontColor = "black";

  private String textProducerFontSize = "40";

  private String textProducerCharSpace = "2";

  private String backGroundClrFrom = "lightGray";

  private String backGroundClrTo = "white";

  private String imageWidth = "200";

  private String imageHeight = "50";

  public Integer getExpireTime() {

    return expireTime;
  }

  public void setExpireTime(Integer expireTime) {

    if (expireTime < 0) {
      expireTime = DEFAULT_EXPIRE_TIME;
    }
    this.expireTime = expireTime;
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
