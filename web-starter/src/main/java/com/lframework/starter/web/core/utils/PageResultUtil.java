package com.lframework.starter.web.core.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.web.core.components.resp.PageResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.NonNull;

/**
 * 分页结果工具类
 * 提供分页数据转换和构建功能，支持MyBatis-Plus和PageHelper的分页结果转换
 * 包括分页信息提取、数据转换、分页结果构建等功能
 *
 * @author lframework@163.com
 */
public class PageResultUtil {

  /**
   * 将MyBatis-Plus分页结果转换为PageResult
   * 不包含额外数据
   *
   * @param page MyBatis-Plus分页结果，不能为null
   * @param <T> 数据类型
   * @return 转换后的分页结果
   */
  public static <T> PageResult<T> convert(@NonNull IPage<T> page) {

    return convert(page, null);
  }

  /**
   * 将PageHelper分页结果转换为PageResult
   * 不包含额外数据
   *
   * @param pageInfo PageHelper分页结果，不能为null
   * @param <T> 数据类型
   * @return 转换后的分页结果
   */
  public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo) {

    return convert(pageInfo, null);
  }

  /**
   * 将MyBatis-Plus分页结果转换为PageResult
   * 支持包含额外数据
   *
   * @param page MyBatis-Plus分页结果，不能为null
   * @param extra 额外数据，可以为null
   * @param <T> 数据类型
   * @return 转换后的分页结果
   */
  public static <T> PageResult<T> convert(@NotNull IPage<T> page,
      Map<Object, Object> extra) {
    PageResult<T> pageResult = new PageResult<>();
    List<T> datas = new ArrayList<T>(page.getRecords());
    pageResult.setDatas(datas);
    pageResult.setHasPrev(page.getCurrent() > 1);
    pageResult.setHasNext(page.getCurrent() < page.getPages());
    pageResult.setPageIndex(page.getCurrent());
    pageResult.setPageSize(page.getSize());
    pageResult.setTotalCount(page.getTotal());
    pageResult.setTotalPage((int) page.getPages());
    if (!ObjectUtil.isEmpty(extra)) {
      pageResult.setExtra(extra);
    }

    return pageResult;
  }

  /**
   * 将PageHelper分页结果转换为PageResult
   * 支持包含额外数据
   *
   * @param pageInfo PageHelper分页结果，不能为null
   * @param extra 额外数据，可以为null
   * @param <T> 数据类型
   * @return 转换后的分页结果
   */
  public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo,
      Map<Object, Object> extra) {

    PageResult<T> pageResult = new PageResult<>();
    List<T> datas = new ArrayList<T>(pageInfo.getList());
    pageResult.setDatas(datas);
    pageResult.setHasNext(pageInfo.isHasNextPage());
    pageResult.setHasPrev(pageInfo.isHasPreviousPage());
    pageResult.setPageIndex(pageInfo.getPageNum());
    pageResult.setPageSize(pageInfo.getPageSize());
    pageResult.setTotalCount(pageInfo.getTotal());
    pageResult.setTotalPage(pageInfo.getPages());
    if (!ObjectUtil.isEmpty(extra)) {
      pageResult.setExtra(extra);
    }

    return pageResult;
  }

  /**
   * 重建分页结果
   * 保留原分页信息，替换数据列表
   *
   * @param pageResult 原分页结果，不能为null
   * @param datas 新的数据列表，可以为null
   * @param <T> 数据类型
   * @return 重建后的分页结果
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> PageResult<T> rebuild(@SuppressWarnings("rawtypes") PageResult pageResult,
      List<T> datas) {

    PageResult<T> result = new PageResult<>();
    BeanUtil.copyProperties(pageResult, result, "datas");

    result.setDatas(datas == null ? CollectionUtil.emptyList() : datas);

    pageResult.setDatas(datas);

    return result;
  }

  /**
   * 创建新的分页结果实例
   * 不包含额外数据
   *
   * @param pageIndex 页码，从1开始
   * @param pageSize 每页大小
   * @param totalCount 总记录数
   * @param datas 数据列表，可以为null
   * @param <T> 数据类型
   * @return 分页结果实例
   */
  public static <T> PageResult<T> newInstance(long pageIndex, long pageSize, long totalCount,
      List<T> datas) {

    return newInstance(pageIndex, pageSize, totalCount, datas, null);
  }

  /**
   * 创建新的分页结果实例
   * 支持包含额外数据
   *
   * @param pageIndex 页码，从1开始
   * @param pageSize 每页大小
   * @param totalCount 总记录数
   * @param datas 数据列表，可以为null
   * @param extra 额外数据，可以为null
   * @param <T> 数据类型
   * @return 分页结果实例
   */
  public static <T> PageResult<T> newInstance(long pageIndex, long pageSize, long totalCount,
      List<T> datas, Map<Object, Object> extra) {
    PageResult<T> pageResult = new PageResult<>();
    pageResult.setTotalCount(totalCount);
    pageResult.setPageSize(pageSize);
    pageResult.setPageIndex(pageIndex);
    pageResult.setTotalPage((int) (totalCount / pageSize) + (totalCount % pageSize > 0 ? 1 : 0));
    pageResult.setDatas(datas);
    pageResult.setExtra(extra);
    pageResult.setHasNext(pageResult.getPageIndex() < pageResult.getTotalPage());
    pageResult.setHasPrev(pageResult.getPageIndex() > 1);

    return pageResult;
  }
}
