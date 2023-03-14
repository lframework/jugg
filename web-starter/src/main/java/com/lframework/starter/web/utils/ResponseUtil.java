package com.lframework.starter.web.utils;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HttpServletResponse工具类
 *
 * @author zmj
 */
@Slf4j
public class ResponseUtil {

  /**
   * 获取response
   *
   * @return
   */
  public static HttpServletResponse getResponse() {

    HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getResponse();

    return response;
  }

  /**
   * 响应错误信息
   *
   * @param response
   * @param e
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
   * 响应Json
   *
   * @param response
   * @param obj
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
   * 下载文件
   *
   * @param file
   */
  public static void download(File file) {

    download(file, file.getName());
  }

  /**
   * 下载文件
   *
   * @param file
   * @param fileName
   */
  public static void download(File file, String fileName) {

    download(file, fileName, "application/octet-stream");
  }

  /**
   * 下载文件
   *
   * @param file
   * @param fileName
   * @param contentType
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
