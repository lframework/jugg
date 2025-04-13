package com.lframework.starter.web.controller;

import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.constants.MybatisConstants;
import com.lframework.starter.web.vo.PageVo;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.components.security.SecurityUtil;

/**
 * BaseController
 *
 * @author zmj
 */
public abstract class BaseController {

  /**
   * 获取当前页码
   *
   * @param vo
   * @return
   */
  public int getPageIndex(PageVo vo) {

    if (ObjectUtil.isNull(vo) || ObjectUtil.isNull(vo.getPageIndex()) || vo.getPageIndex() <= 0) {
      return MybatisConstants.DEFAULT_PAGE_INDEX;
    }

    return vo.getPageIndex();
  }

  /**
   * 获取每页条数
   *
   * @param vo
   * @return
   */
  public int getPageSize(PageVo vo) {

    if (ObjectUtil.isNull(vo) || ObjectUtil.isNull(vo.getPageSize()) || vo.getPageSize() <= 0) {
      return MybatisConstants.DEFAULT_PAGE_SIZE;
    }

    return vo.getPageSize();
  }

  /**
   * 获取当前页码
   *
   * @return
   */
  public int getPageIndex() {

    return getPageIndex(null);
  }

  /**
   * 获取每页条数
   *
   * @return
   */
  public int getPageSize() {

    return getPageSize(null);
  }

  /**
   * 获取当前登录用户信息
   *
   * @return
   */
  public AbstractUserDetails getCurrentUser() {

    return SecurityUtil.getCurrentUser();
  }
}
