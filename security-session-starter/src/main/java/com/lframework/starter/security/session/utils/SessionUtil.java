package com.lframework.starter.security.session.utils;

import com.lframework.starter.web.utils.RequestUtil;
import javax.servlet.http.HttpSession;

/**
 * Session工具类
 *
 * @author zmj
 */
public class SessionUtil {

  public static HttpSession getSession() {

    return RequestUtil.getRequest().getSession(false);
  }
}
