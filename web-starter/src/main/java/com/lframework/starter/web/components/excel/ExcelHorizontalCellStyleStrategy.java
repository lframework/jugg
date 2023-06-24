package com.lframework.starter.web.components.excel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.lframework.starter.common.utils.CollectionUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

public class ExcelHorizontalCellStyleStrategy extends AbstractCellStyleStrategy {

  private WriteCellStyle headWriteCellStyle;
  private WriteCellStyle requiredFieldHeadWriteCellStyle;
  private List<WriteCellStyle> contentWriteCellStyleList;

  private CellStyle headCellStyle;
  private CellStyle requiredFieldHeadCellStyle;
  private List<CellStyle> contentCellStyleList;

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
  protected void initCellStyle(Workbook workbook) {
    if (headWriteCellStyle != null) {
      headCellStyle = StyleUtil.buildHeadCellStyle(workbook, headWriteCellStyle);
    }
    if (headWriteCellStyle != null) {
      requiredFieldHeadCellStyle = StyleUtil.buildHeadCellStyle(workbook, requiredFieldHeadWriteCellStyle);
    }
    if (contentWriteCellStyleList != null && !contentWriteCellStyleList.isEmpty()) {
      contentCellStyleList = new ArrayList<CellStyle>();
      for (WriteCellStyle writeCellStyle : contentWriteCellStyleList) {
        contentCellStyleList.add(StyleUtil.buildContentCellStyle(workbook, writeCellStyle));
      }
    }
  }

  @Override
  protected void setHeadCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
    if (headCellStyle == null) {
      return;
    }
    cell.setCellStyle(
        CollectionUtil.isNotEmpty(this.requiredFiledNames) && this.requiredFiledNames.contains(
            head.getFieldName()) ? requiredFieldHeadCellStyle : headCellStyle);
  }

  @Override
  protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
    if (contentCellStyleList == null || contentCellStyleList.isEmpty()) {
      return;
    }
    cell.setCellStyle(contentCellStyleList.get(relativeRowIndex % contentCellStyleList.size()));
  }
}
