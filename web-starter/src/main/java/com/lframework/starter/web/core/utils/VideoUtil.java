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

/**
 * 视频工具类
 * 提供视频处理相关的工具方法，支持视频帧提取和图片保存
 * 包括视频帧提取、图片保存等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class VideoUtil {

  /**
   * 获取视频指定帧的图片
   * 从视频文件中提取指定帧号的图片
   *
   * @param file 视频文件，不能为null
   * @param frameNumber 帧号，不能为null
   * @return 图片对象，如果提取失败则抛出异常
   * @throws DefaultSysException 当视频处理失败时抛出
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
   * 获取视频指定帧的图片并保存
   * 从视频文件中提取指定帧号的图片并保存到指定路径
   *
   * @param file 视频文件，不能为null
   * @param frameNumber 帧号，不能为null
   * @param fullPath 保存路径，不能为null
   * @return 保存的图片文件，如果处理失败则抛出异常
   * @throws DefaultSysException 当视频处理失败时抛出
   */
  public static File getImage(File file, Integer frameNumber, String fullPath) {
    BufferedImage bufferedImage = getImage(file, frameNumber);
    return savePngImage(bufferedImage, fullPath);
  }

  /**
   * 保存图片为PNG格式
   * 将BufferedImage保存为PNG格式的图片文件
   *
   * @param image 图片对象，不能为null
   * @param fullPath 保存路径，不能为null
   * @return 保存的图片文件，如果保存失败则抛出异常
   * @throws DefaultSysException 当图片保存失败时抛出
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
