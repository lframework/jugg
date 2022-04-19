package com.lframework.starter.security.controller;

import com.lframework.starter.web.controller.BaseController;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;

/**
 * 具有Security能力的BaseController
 *
 * @author zmj
 */
public abstract class SecurityController extends BaseController {

  /**
   * 获取当前登录用户信息
   *
   * @return
   */
  public AbstractUserDetails getCurrentUser() {

    return SecurityUtil.getCurrentUser();
  }
}
