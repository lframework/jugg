package com.lframework.starter.web.core.utils;

import com.github.pagehelper.PageHelper;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.core.constants.MybatisConstants;
import com.lframework.starter.web.core.vo.PageVo;

/**
 * 分页插件工具类
 * 提供PageHelper分页插件的便捷操作方法
 * 包括分页开启、参数处理、默认值设置等功能
 *
 * @author lframework@163.com
 */
public class PageHelperUtil {

  /**
   * 开启分页
   * 使用指定的页码和页大小开启分页
   *
   * @param pageIndex 页码，从1开始，会自动调整为最小值1
   * @param pageSize 每页大小，会自动调整为最小值1
   */
  public static void startPage(int pageIndex, int pageSize) {

    pageIndex = Math.max(pageIndex, 1);
    pageSize = Math.max(pageSize, 1);

    PageHelper.startPage(pageIndex, pageSize);
  }

  /**
   * 根据分页VO开启分页
   * 使用分页VO中的参数开启分页，如果参数为空则使用默认值
   *
   * @param pageVo 分页VO，可以为null
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
