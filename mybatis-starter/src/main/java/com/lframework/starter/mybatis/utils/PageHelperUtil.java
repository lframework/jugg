package com.lframework.starter.mybatis.utils;

import com.github.pagehelper.PageHelper;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.web.vo.PageVo;

/**
 * 分页插件Util
 *
 * @author zmj
 */
public class PageHelperUtil {

  /**
   * 默认当前页码
   */
  private static final int DEFAULT_PAGE_INDEX = 1;

  /**
   * 默认每页条数
   */
  private static final int DEFAULT_PAGE_SIZE = 20;

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
      startPage(DEFAULT_PAGE_INDEX, DEFAULT_PAGE_SIZE);
      return;
    }

    if (ObjectUtil.isNull(pageVo.getPageIndex())) {
      pageVo.setPageIndex(DEFAULT_PAGE_INDEX);
    }

    if (ObjectUtil.isNull(pageVo.getPageSize())) {
      pageVo.setPageSize(DEFAULT_PAGE_SIZE);
    }

    startPage(pageVo.getPageIndex(), pageVo.getPageSize());
  }
}
