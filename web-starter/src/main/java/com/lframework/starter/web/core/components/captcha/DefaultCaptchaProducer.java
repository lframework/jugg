package com.lframework.starter.web.core.components.captcha;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.config.properties.KaptchaProperties;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.security.SecureRandom;

public class DefaultCaptchaProducer implements CaptchaProducer {

  private static final int DEFAULT_NOISE_LINES = 8;

  private final KaptchaProperties properties;

  private final SecureRandom secureRandom = new SecureRandom();

  public DefaultCaptchaProducer(KaptchaProperties properties) {
    this.properties = properties;
  }

  @Override
  public String createText() {
    String chars = StringUtil.isBlank(properties.getTextProducerCharString())
        ? "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz"
        : properties.getTextProducerCharString();
    int length = parsePositiveInt(properties.getTextProducerCharLength(), 4);

    StringBuilder builder = new StringBuilder(length);
    for (int i = 0; i < length; i++) {
      builder.append(chars.charAt(secureRandom.nextInt(chars.length())));
    }
    return builder.toString();
  }

  @Override
  public BufferedImage createImage(String text) {
    int width = parsePositiveInt(properties.getImageWidth(), 120);
    int height = parsePositiveInt(properties.getImageHeight(), 40);
    int fontSize = parsePositiveInt(properties.getTextProducerFontSize(), 28);
    int charSpace = parsePositiveInt(properties.getTextProducerCharSpace(), 2);

    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    Graphics2D graphics = image.createGraphics();
    try {
      graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

      graphics.setPaint(new GradientPaint(0, 0,
          parseColor(properties.getBackGroundClrFrom(), Color.LIGHT_GRAY),
          0, height,
          parseColor(properties.getBackGroundClrTo(), Color.WHITE)));
      graphics.fillRect(0, 0, width, height);

      drawNoise(graphics, width, height);
      drawText(graphics, text, width, height, fontSize, charSpace);
      drawBorder(graphics, width, height);
    } finally {
      graphics.dispose();
    }
    return image;
  }

  private void drawNoise(Graphics2D graphics, int width, int height) {
    graphics.setColor(parseColor(properties.getNoiseColor(), Color.GRAY));
    for (int i = 0; i < DEFAULT_NOISE_LINES; i++) {
      int x1 = secureRandom.nextInt(width);
      int y1 = secureRandom.nextInt(height);
      int x2 = secureRandom.nextInt(width);
      int y2 = secureRandom.nextInt(height);
      graphics.drawLine(x1, y1, x2, y2);
    }
  }

  private void drawText(Graphics2D graphics, String text, int width, int height, int fontSize,
      int charSpace) {
    String[] fontNames = StringUtil.isBlank(properties.getTextProducerFontNames())
        ? new String[]{"SansSerif"}
        : properties.getTextProducerFontNames().split(",");
    Color fontColor = parseColor(properties.getTextProducerFontColor(), Color.BLACK);
    int step = Math.max(fontSize, width / Math.max(1, text.length() + 1));
    int totalTextWidth = text.isEmpty() ? 0 : text.length() * step + (text.length() - 1) * charSpace;
    int x = Math.max(charSpace, (width - totalTextWidth) / 2);

    for (int i = 0; i < text.length(); i++) {
      String fontName = fontNames[secureRandom.nextInt(fontNames.length)].trim();
      Font font = new Font(StringUtil.isBlank(fontName) ? "SansSerif" : fontName, Font.BOLD,
          fontSize);
      graphics.setFont(font);
      graphics.setColor(fontColor);

      int y = height / 2 + fontSize / 3 + secureRandom.nextInt(7) - 3;
      double angle = Math.toRadians(secureRandom.nextInt(31) - 15);

      AffineTransform originalTransform = graphics.getTransform();
      graphics.rotate(angle, x, y);
      graphics.drawString(String.valueOf(text.charAt(i)), x, y);
      graphics.setTransform(originalTransform);

      x += step + charSpace;
    }
  }

  private void drawBorder(Graphics2D graphics, int width, int height) {
    if (!Boolean.TRUE.equals(properties.getBorder())) {
      return;
    }

    graphics.setColor(parseColor(properties.getBorderColor(), Color.BLACK));
    graphics.setStroke(new BasicStroke(parsePositiveInt(properties.getBorderThickness(), 1)));
    graphics.drawRect(0, 0, width - 1, height - 1);
  }

  private int parsePositiveInt(String value, int defaultValue) {
    if (StringUtil.isBlank(value)) {
      return defaultValue;
    }
    try {
      int parsed = Integer.parseInt(value.trim());
      return parsed > 0 ? parsed : defaultValue;
    } catch (NumberFormatException e) {
      return defaultValue;
    }
  }

  private Color parseColor(String value, Color defaultColor) {
    if (StringUtil.isBlank(value)) {
      return defaultColor;
    }

    String trimmed = value.trim();
    try {
      if (trimmed.startsWith("#")) {
        return Color.decode(trimmed);
      }

      if (trimmed.contains(",")) {
        String[] rgb = trimmed.split(",");
        if (rgb.length == 3) {
          return new Color(Integer.parseInt(rgb[0].trim()), Integer.parseInt(rgb[1].trim()),
              Integer.parseInt(rgb[2].trim()));
        }
      }

      for (Field field : Color.class.getFields()) {
        if (field.getType() == Color.class && field.getName().equalsIgnoreCase(trimmed)) {
          return (Color) field.get(null);
        }
      }
    } catch (Exception e) {
      return defaultColor;
    }

    return defaultColor;
  }
}
