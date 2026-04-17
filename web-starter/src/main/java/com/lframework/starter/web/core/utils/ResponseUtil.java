package com.lframework.starter.web.core.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HTTP响应工具类
 * 提供HTTP响应相关的工具方法，支持响应数据输出和文件下载
 * 包括JSON响应、错误响应、文件下载等功能
 *
 * @author lframework@163.com
 */
@Slf4j
public class ResponseUtil {

  /**
   * 获取当前HTTP响应对象
   * 从Spring的RequestContextHolder中获取当前响应
   *
   * @return HTTP响应对象
   */
  public static HttpServletResponse getResponse() {

    HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();

    return response;
  }

  /**
   * 响应错误信息
   * 将错误信息以JSON格式响应给客户端
   *
   * @param response HTTP响应对象，不能为null
   * @param e 业务异常，不能为null
   * @throws DefaultSysException 当响应写入失败时抛出
   */
  public static void respFailJson(HttpServletResponse response, BaseException e) {

    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StringPool.CHARACTER_ENCODING_UTF_8);

    try {
      response.getWriter().print(JsonUtil.toJsonString(InvokeResultBuilder.fail(e)));
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
      throw new DefaultSysException();
    }
  }

  /**
   * 响应成功JSON
   * 将成功数据以JSON格式响应给客户端
   *
   * @param response HTTP响应对象，不能为null
   * @param obj 响应数据，可以为null
   * @throws DefaultSysException 当响应写入失败时抛出
   */
  public static void respSuccessJson(HttpServletResponse response, Object obj) {

    response.setStatus(HttpStatus.OK.value());
    response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding(StringPool.CHARACTER_ENCODING_UTF_8);

    try {
      response.getWriter().print(JsonUtil.toJsonString(InvokeResultBuilder.success(obj)));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException();
    }
  }

  /**
   * 下载文件（使用文件名）
   * 使用文件的原文件名进行下载
   *
   * @param file 要下载的文件，不能为null
   */
  public static void download(File file) {

    download(file, file.getName());
  }

  /**
   * 下载文件（指定文件名）
   * 使用指定的文件名进行下载
   *
   * @param file 要下载的文件，不能为null
   * @param fileName 下载文件名，不能为null
   */
  public static void download(File file, String fileName) {

    download(file, fileName, "application/octet-stream");
  }

  /**
   * 下载文件（指定文件名和内容类型）
   * 使用指定的文件名和内容类型进行下载
   *
   * @param file 要下载的文件，不能为null
   * @param fileName 下载文件名，不能为null
   * @param contentType 内容类型，不能为null
   * @throws DefaultSysException 当文件读取失败时抛出
   */
  public static void download(File file, String fileName, String contentType) {

    HttpServletResponse response = getResponse();
    response.setContentType(contentType);
    response.setCharacterEncoding(StandardCharsets.UTF_8.name());
    response.setHeader("FileName", fileName);
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

    OutputStream os = null;
    try {
      os = response.getOutputStream();
      os.write(FileUtil.readBytes(file));
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }
}
