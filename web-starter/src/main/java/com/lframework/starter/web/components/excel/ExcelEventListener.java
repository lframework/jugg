package com.lframework.starter.web.components.excel;

import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

/**
 * Excel事件监听器 用于解析Excel
 * @param <T>
 *
 * @author zmj
 */
@Slf4j
public abstract class ExcelEventListener<T extends ExcelModel> extends AnalysisEventListener<T> {

}
