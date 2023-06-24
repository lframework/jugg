package com.lframework.starter.web.utils;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * HttpServeletRequest工具类
 *
 * @author zmj
 */
@Slf4j
public class RequestUtil {

  /**
   * 获取request
   *
   * @return
   */
  public static HttpServletRequest getRequest() {

    HttpServletRequest request = ((ServletRequestAttributes) (RequestContextHolder
        .currentRequestAttributes())).getRequest();

    return request;
  }

  /**
   * 获取response
   *
   * @return
   */
  public static HttpServletResponse getResponse() {

    HttpServletResponse response = ((ServletRequestAttributes) (RequestContextHolder
        .currentRequestAttributes())).getResponse();

    return response;
  }

  /**
   * 获取所有Request Header
   *
   * @return
   */
  public static Map<String, String> getHeaders() {

    HttpServletRequest request = getRequest();

    Enumeration<String> headerNames = request.getHeaderNames();
    if (CollectionUtil.isEmpty(headerNames)) {
      return CollectionUtil.emptyMap();
    }

    Map<String, String> headers = new HashMap<>();
    while (headerNames.hasMoreElements()) {
      String headerName = headerNames.nextElement();
      headers.put(headerName, request.getHeader(headerName));
    }

    return headers;
  }

  /**
   * 获取请求参数
   *
   * @param request
   * @return
   */
  public static Map<String, Object> getParameters(HttpServletRequest request) {

    Map<String, String[]> parameterMap = request.getParameterMap();
    Map<String, Object> parameters = new HashMap<>(parameterMap.size());

    parameterMap.entrySet().stream().forEach(entry -> {
      String[] values = entry.getValue();
      if (!ArrayUtil.isEmpty(values)) {
        parameters.put(entry.getKey(), values[0]);
      }
    });

    return parameters;
  }

  public static byte[] getRequestBody(HttpServletRequest request) {

    int len = request.getContentLength();
    ServletInputStream is = null;
    try {
      is = request.getInputStream();

      byte[] buffer = new byte[len];
      is.read(buffer, 0, len);

      return buffer;
    } catch (IOException e) {
      log.error(e.getMessage(), e);

      return null;
    }
  }

  public static byte[] getRequestBody() {

    HttpServletRequest request = getRequest();
    return getRequestBody(request);
  }

  public static String getRequestBodyStr(HttpServletRequest request) {
    byte[] bytes = getRequestBody(request);

    return new String(bytes, StandardCharsets.UTF_8);
  }

  public static String getRequestBodyStr() {
    byte[] bytes = getRequestBody();

    return new String(bytes, StandardCharsets.UTF_8);
  }

  /**
   * 获取请求参数
   *
   * @return
   */
  public static Map<String, Object> getParameters() {

    Map<String, String[]> parameterMap = getRequest().getParameterMap();
    Map<String, Object> parameters = new HashMap<>(parameterMap.size());

    parameterMap.entrySet().stream().forEach(entry -> {
      String[] values = entry.getValue();
      if (!ArrayUtil.isEmpty(values)) {
        parameters.put(entry.getKey(), values[0]);
      }
    });

    return parameters;
  }

  /**
   * 获取请求的客户端ip
   *
   * @return
   */
  public static String getRequestIp() {

    HttpServletRequest request = getRequest();

    String ip = request.getHeader("x-forwarded-for");
    if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
      // 多次反向代理后会有多个ip值，第一个ip才是真实ip
      if (ip.indexOf(",") != -1) {
        ip = ip.split(",")[0];
      }
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_CLIENT_IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getHeader("X-Real-IP");
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }
    return ip;
  }
}
