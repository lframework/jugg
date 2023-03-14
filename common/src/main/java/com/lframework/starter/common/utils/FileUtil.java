package com.lframework.starter.common.utils;

import java.util.Arrays;
import java.util.List;

public class FileUtil extends cn.hutool.core.io.FileUtil {

  /**
   * 图片后缀名
   */
  public static final List<String> IMG_SUFFIX = Arrays.asList("jpg", "jpeg", "bpm", "png", "gif");

  /**
   * Excel文件后缀名
   */
  public static final List<String> EXCEL_SUFFIX = Arrays.asList("xls", "xlsx");

  /**
   * 视频文件后缀名
   */
  public static final List<String> VIDEO_SUFFIX = Arrays.asList("avi", "wmv", "mpeg", "mp4", "m4v",
      "mov", "asf", "flv", "f4v", "rmvb", "rm", "3gp", "vob");
}
