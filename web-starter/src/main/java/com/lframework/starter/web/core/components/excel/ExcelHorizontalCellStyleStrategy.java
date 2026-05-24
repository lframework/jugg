package com.lframework.starter.web.core.components.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.lframework.starter.common.utils.CollectionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ExcelHorizontalCellStyleStrategy extends AbstractCellStyleStrategy {

  private WriteCellStyle headWriteCellStyle;
  private WriteCellStyle requiredFieldHeadWriteCellStyle;
  private List<WriteCellStyle> contentWriteCellStyleList;

  private Set<String> requiredFiledNames;

  public ExcelHorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle,
      WriteCellStyle requiredFieldHeadWriteCellStyle,
      List<WriteCellStyle> contentWriteCellStyleList, Set<String> requiredFiledNames) {
    this.headWriteCellStyle = headWriteCellStyle;
    this.requiredFieldHeadWriteCellStyle = requiredFieldHeadWriteCellStyle;
    this.contentWriteCellStyleList = contentWriteCellStyleList;
    this.requiredFiledNames = requiredFiledNames;
  }

  public ExcelHorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle,
      WriteCellStyle requiredFieldHeadWriteCellStyle,
      WriteCellStyle contentWriteCellStyle, Set<String> requiredFiledNames) {
    this.headWriteCellStyle = headWriteCellStyle;
    this.requiredFieldHeadWriteCellStyle = requiredFieldHeadWriteCellStyle;
    contentWriteCellStyleList = new ArrayList<>();
    contentWriteCellStyleList.add(contentWriteCellStyle);
    this.requiredFiledNames = requiredFiledNames;
  }

  @Override
  protected void setHeadCellStyle(CellWriteHandlerContext context) {
    if (context.getFirstCellData() == null || headWriteCellStyle == null) {
      return;
    }

    Head head = context.getHeadData();
    WriteCellStyle writeCellStyle = CollectionUtil.isNotEmpty(this.requiredFiledNames)
        && head != null && this.requiredFiledNames.contains(head.getFieldName())
        ? requiredFieldHeadWriteCellStyle : headWriteCellStyle;
    mergeStyle(writeCellStyle, context.getFirstCellData());
  }

  @Override
  protected void setContentCellStyle(CellWriteHandlerContext context) {
    if (context.getFirstCellData() == null || contentWriteCellStyleList == null
        || contentWriteCellStyleList.isEmpty()) {
      return;
    }

    Integer relativeRowIndex = context.getRelativeRowIndex();
    int index = relativeRowIndex == null || relativeRowIndex <= 0 ? 0
        : relativeRowIndex % contentWriteCellStyleList.size();
    mergeStyle(contentWriteCellStyleList.get(index), context.getFirstCellData());
  }

  private void mergeStyle(WriteCellStyle writeCellStyle, WriteCellData<?> cellData) {
    if (writeCellStyle != null) {
      WriteCellStyle.merge(writeCellStyle, cellData.getOrCreateStyle());
    }
  }
}
