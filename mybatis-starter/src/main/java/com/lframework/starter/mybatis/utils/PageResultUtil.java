package com.lframework.starter.mybatis.utils;

import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分页数据Util
 *
 * @author zmj
 */
public class PageResultUtil {

    public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo) {

        return convert(pageInfo, null);
    }

    public static <T> PageResult<T> convert(@NonNull PageInfo<T> pageInfo, Map<Object, Object> extra) {

        PageResult<T> pageResult = new PageResult<>();
        List<T> datas = new ArrayList<T>(pageInfo.getList());
        pageResult.setDatas(datas);
        pageResult.setHasNext(pageInfo.isHasNextPage());
        pageResult.setHasPrev(pageInfo.isHasPreviousPage());
        pageResult.setPageIndex((long) pageInfo.getPageNum());
        pageResult.setPageSize((long) pageInfo.getPageSize());
        pageResult.setTotalCount(pageInfo.getTotal());
        pageResult.setTotalPage(pageInfo.getPages());
        if (!ObjectUtil.isEmpty(extra)) {
            pageResult.setExtra(extra);
        }

        return pageResult;
    }

    public static <T> void rebuild(PageResult<T> pageResult, List datas) {

        pageResult.setDatas(datas);
    }
}
