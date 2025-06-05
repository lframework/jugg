package com.lframework.starter.web.core.utils;

import com.github.pagehelper.PageHelper;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.core.constants.MybatisConstants;
import com.lframework.starter.web.core.vo.PageVo;

/**
 * 分页插件Util
 *
 * @author zmj
 */
public class PageHelperUtil {

  /**
   * 开启分页
   *
   * @param pageIndex
   * @param pageSize
   */
  public static void startPage(int pageIndex, int pageSize) {

    pageIndex = Math.max(pageIndex, 1);
    pageSize = Math.max(pageSize, 1);

    PageHelper.startPage(pageIndex, pageSize);
  }

  /**
   * 根据Vo开启分页
   *
   * @param pageVo
   */
  public static void startPage(PageVo pageVo) {

    if (ObjectUtil.isNull(pageVo)) {
      startPage(MybatisConstants.DEFAULT_PAGE_INDEX, MybatisConstants.DEFAULT_PAGE_SIZE);
      return;
    }

    if (ObjectUtil.isNull(pageVo.getPageIndex())) {
      pageVo.setPageIndex(MybatisConstants.DEFAULT_PAGE_INDEX);
    }

    if (ObjectUtil.isNull(pageVo.getPageSize())) {
      pageVo.setPageSize(MybatisConstants.DEFAULT_PAGE_SIZE);
    }

    startPage(pageVo.getPageIndex(), pageVo.getPageSize());
  }
}
