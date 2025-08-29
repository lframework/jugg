package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import lombok.extern.slf4j.Slf4j;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.JCodecException;
import org.jcodec.common.model.Picture;
import org.jcodec.scale.AWTUtil;

@Slf4j
public class VideoUtil {

  /**
   * 获取视频指定帧的图片
   *
   * @param file
   * @param frameNumber
   * @return
   */
  public static BufferedImage getImage(File file, Integer frameNumber) {
    Picture picture = null;
    try {
      picture = FrameGrab.getFrameFromFile(file, frameNumber);
    } catch (IOException | JCodecException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
    BufferedImage bufferedImage = AWTUtil.toBufferedImage(picture);

    return bufferedImage;
  }

  /**
   * 获取视频指定帧的图片
   *
   * @param file
   * @param frameNumber
   * @param fullPath
   * @return
   */
  public static File getImage(File file, Integer frameNumber, String fullPath) {
    BufferedImage bufferedImage = getImage(file, frameNumber);
    return savePngImage(bufferedImage, fullPath);
  }

  /**
   * 保存图片
   *
   * @param image
   * @param fullPath
   * @return
   */
  public static File savePngImage(BufferedImage image, String fullPath) {
    try {
      File saveFile = new File(fullPath);
      ImageIO.write(image, "png", new File(fullPath));
      return saveFile;
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }
}
