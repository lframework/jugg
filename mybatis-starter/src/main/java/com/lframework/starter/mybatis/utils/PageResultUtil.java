package com.lframework.starter.mybatis.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.NonNull;

/**
 * 分页数据Util
 *
 * @author zmj
 */
public class PageResultUtil {

  public static <T> PageResult<T> convert(@NonNull IPage<T> page) {

    return convert(page, null);
  }

  public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo) {

    return convert(pageInfo, null);
  }

  public static <T> PageResult<T> convert(@NotNull IPage<T> page,
      Map<Object, Object> extra) {
    PageResult<T> pageResult = new PageResult<>();
    List<T> datas = new ArrayList<T>(page.getRecords());
    pageResult.setDatas(datas);
    pageResult.setHasNext(page.getCurrent() > 1);
    pageResult.setHasPrev(page.getCurrent() < page.getPages());
    pageResult.setPageIndex(page.getCurrent());
    pageResult.setPageSize(page.getSize());
    pageResult.setTotalCount(page.getTotal());
    pageResult.setTotalPage((int) page.getPages());
    if (!ObjectUtil.isEmpty(extra)) {
      pageResult.setExtra(extra);
    }

    return pageResult;
  }

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

  @SuppressWarnings({"rawtypes", "unchecked"})
  public static <T> PageResult<T> rebuild(@SuppressWarnings("rawtypes") PageResult pageResult,
      List<T> datas) {

    PageResult<T> result = new PageResult<>();
    BeanUtil.copyProperties(pageResult, result, "datas");

    result.setDatas(datas == null ? CollectionUtil.emptyList() : datas);

    pageResult.setDatas(datas);

    return result;
  }

  public static <T> PageResult<T> newInstance(long pageIndex, long pageSize, long totalCount,
      List<T> datas) {

    return newInstance(pageIndex, pageSize, totalCount, datas, null);
  }

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
