package com.lframework.common.utils;

import java.util.Arrays;
import java.util.List;

public class FileUtil extends cn.hutool.core.io.FileUtil {

    /**
     * 图片后缀名
     */
    public static final List<String> IMG_SUFFIX = Arrays.asList("jpg", "jpeg", "bpm", "png", "gif");

    /**
     * Excel文件后缀名
     */
    public static final List<String> EXCEL_SUFFIX = Arrays.asList("xls", "xlsx");
}
