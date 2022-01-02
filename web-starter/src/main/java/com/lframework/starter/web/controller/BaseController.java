package com.lframework.starter.web.controller;

import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.web.vo.PageVo;

/**
 * BaseController
 *
 * @author zmj
 */
public abstract class BaseController {

    /**
     * 默认当前页码
     */
    private static final int DEFAULT_PAGE_INDEX = 1;

    /**
     * 默认每页条数
     */
    private static final int DEFAULT_PAGE_SIZE = 20;

    /**
     * 默认导出条数
     */
    private static final int DEFAULT_EXPORT_SIZE = 2000;

    /**
     * 获取当前页码
     * @param vo
     * @return
     */
    public int getPageIndex(PageVo vo) {

        if (ObjectUtil.isNull(vo) || ObjectUtil.isNull(vo.getPageIndex()) || vo.getPageIndex() <= 0) {
            return DEFAULT_PAGE_INDEX;
        }

        return vo.getPageIndex();
    }

    /**
     * 获取每页条数
     * @param vo
     * @return
     */
    public int getPageSize(PageVo vo) {

        if (ObjectUtil.isNull(vo) || ObjectUtil.isNull(vo.getPageSize()) || vo.getPageSize() <= 0) {
            return DEFAULT_PAGE_SIZE;
        }

        return vo.getPageSize();
    }

    /**
     * 获取当前页码
     * @return
     */
    public int getPageIndex() {

        return getPageIndex(null);
    }

    /**
     * 获取每页条数
     * @return
     */
    public int getPageSize() {

        return getPageSize(null);
    }

    public int getExportSize() {

        return DEFAULT_EXPORT_SIZE;
    }
}
