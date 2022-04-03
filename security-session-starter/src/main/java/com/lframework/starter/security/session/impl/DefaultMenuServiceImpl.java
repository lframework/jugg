package com.lframework.starter.security.session.impl;

import com.lframework.starter.security.impl.AbstractMenuServiceImpl;
import com.lframework.starter.security.session.utils.SessionUtil;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;

public class DefaultMenuServiceImpl extends AbstractMenuServiceImpl {

  @Override
  protected Map<String, Object> getDefaultVars() {
    Map<String, Object> vars = new HashMap<>();
    HttpSession session = SessionUtil.getSession();
    if (session != null) {
      vars.put("_sessionId", session.getId());
    }

    return vars;
  }
}
