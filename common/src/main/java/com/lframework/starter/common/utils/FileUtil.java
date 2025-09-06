package com.lframework.starter.common.utils;

import java.util.Arrays;
import java.util.List;

/**
 * 文件工具类
 * 基于HuTool的FileUtil进行扩展，提供文件操作相关的工具方法
 * 包括文件类型判断、文件操作、路径处理等功能
 *
 * @author lframework@163.com
 */
public class FileUtil extends cn.hutool.core.io.FileUtil {

  /**
   * 图片文件后缀名列表
   * 支持常见的图片格式：jpg、jpeg、bmp、png、gif
   */
  public static final List<String> IMG_SUFFIX = Arrays.asList("jpg", "jpeg", "bmp", "png", "gif");

  /**
   * Excel文件后缀名列表
   * 支持Excel格式：xls、xlsx
   */
  public static final List<String> EXCEL_SUFFIX = Arrays.asList("xls", "xlsx");

  /**
   * 视频文件后缀名列表
   * 支持常见的视频格式：avi、wmv、mpeg、mp4、m4v、mov、asf、flv、f4v、rmvb、rm、3gp、vob
   */
  public static final List<String> VIDEO_SUFFIX = Arrays.asList("avi", "wmv", "mpeg", "mp4", "m4v",
      "mov", "asf", "flv", "f4v", "rmvb", "rm", "3gp", "vob");
}
