package com.lframework.starter.web.utils;

import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.FileUtil;
import com.lframework.starter.web.components.excel.ExcelModel;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterBuilder;
import com.lframework.starter.web.components.excel.ExcelMultipartWriterSheetBuilder;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * Excel工具类
 *
 * @author zmj
 */
@Slf4j
public class ExcelUtil {

  /**
   * 默认列宽策略
   */
  private static final WriteHandler DEFAULT_COLUMN_WIDTH_STYLE_STRATEGY = new LongestMatchColumnWidthStyleStrategy();

  /**
   * 导出Xls至Response
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(String sheetName, Class<T> clazz) {

    exportXls(sheetName, sheetName, clazz, Collections.EMPTY_LIST, null);
  }

  /**
   * 导出Xls至Response
   *
   * @param sheetName
   * @param clazz
   * @param datas
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(String sheetName, Class<T> clazz,
      List<T> datas) {

    exportXls(sheetName, sheetName, clazz, datas, null);
  }

  /**
   * 导出Xls至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(String fileName, String sheetName,
      Class<T> clazz) {

    exportXls(fileName, sheetName, clazz, Collections.EMPTY_LIST, null);
  }

  /**
   * 导出Xls至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param datas
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(String fileName, String sheetName,
      Class<T> clazz,
      List<T> datas) {

    exportXls(fileName, sheetName, clazz, datas, null);
  }

  /**
   * 导出Xls至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param datas
   * @param writeHandlers
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(String fileName, String sheetName,
      Class<T> clazz,
      List<T> datas, List<WriteHandler> writeHandlers) {

    HttpServletResponse response = ResponseUtil.getResponse();
    try (OutputStream os = response.getOutputStream()) {
      fileName = URLEncoder
          .encode(fileName + ExcelTypeEnum.XLS.getValue(), StandardCharsets.UTF_8.name());
      response.setContentType("application/msexcel");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader("FileName", fileName);
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

      exportExcel(os, sheetName, ExcelTypeEnum.XLS, clazz, datas, writeHandlers);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException("Xls导出异常");
    }
  }

  /**
   * 导出Xlsx至Response
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(String sheetName, Class<T> clazz) {

    exportXlsx(sheetName, sheetName, clazz, Collections.EMPTY_LIST, null);
  }

  /**
   * 导出Xlsx至Response
   *
   * @param sheetName
   * @param clazz
   * @param datas
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(String sheetName, Class<T> clazz,
      List<T> datas) {

    exportXlsx(sheetName, sheetName, clazz, datas, null);
  }

  /**
   * 导出Xlsx至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(String fileName, String sheetName,
      Class<T> clazz) {

    exportXlsx(fileName, sheetName, clazz, Collections.EMPTY_LIST, null);
  }

  /**
   * 导出Xlsx至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param datas
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(String fileName, String sheetName,
      Class<T> clazz,
      List<T> datas) {

    exportXlsx(fileName, sheetName, clazz, datas, null);
  }

  /**
   * 导出Xlsx至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param datas
   * @param writeHandlers
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(String fileName, String sheetName,
      Class<T> clazz,
      List<T> datas, List<WriteHandler> writeHandlers) {

    HttpServletResponse response = ResponseUtil.getResponse();
    try (OutputStream os = response.getOutputStream()) {
      fileName = URLEncoder
          .encode(fileName + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8.name());
      response.setContentType("application/vnd.ms-excel");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader("FileName", fileName);
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

      exportExcel(os, sheetName, ExcelTypeEnum.XLSX, clazz, datas, writeHandlers);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException("Xls导出异常");
    }
  }

  /**
   * 导出Xls至文件
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(File file, String sheetName, Class<T> clazz) {

    exportXls(file, sheetName, clazz, Collections.EMPTY_LIST, null);
  }

  /**
   * 导出Xls至文件
   *
   * @param sheetName
   * @param clazz
   * @param datas
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(File file, String sheetName, Class<T> clazz,
      List<T> datas) {

    exportXls(file, sheetName, clazz, datas, null);
  }

  /**
   * 导出Xls至文件
   *
   * @param sheetName
   * @param clazz
   * @param datas
   * @param writeHandlers
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(File file, String sheetName, Class<T> clazz,
      List<T> datas,
      List<WriteHandler> writeHandlers) {

    exportExcel(FileUtil.getOutputStream(file), sheetName, ExcelTypeEnum.XLS, clazz, datas,
        writeHandlers);
  }

  /**
   * 导出Xlsx至文件
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(File file, String sheetName,
      Class<T> clazz) {

    exportXlsx(file, sheetName, clazz, Collections.EMPTY_LIST, null);
  }

  /**
   * 导出Xlsx至文件
   *
   * @param sheetName
   * @param clazz
   * @param datas
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(File file, String sheetName, Class<T> clazz,
      List<T> datas) {

    exportXlsx(file, sheetName, clazz, datas, null);
  }

  /**
   * 导出Xlsx至文件
   *
   * @param sheetName
   * @param clazz
   * @param datas
   * @param writeHandlers
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXlsx(File file, String sheetName, Class<T> clazz,
      List<T> datas,
      List<WriteHandler> writeHandlers) {

    exportExcel(FileUtil.getOutputStream(file), sheetName, ExcelTypeEnum.XLSX, clazz, datas,
        writeHandlers);
  }

  /**
   * 分段导出Xls至Response
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXls(
      String sheetName,
      Class<T> clazz) {

    return multipartExportXls(sheetName, sheetName, clazz);
  }

  /**
   * 分段导出Xls至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXls(
      String fileName,
      String sheetName, Class<T> clazz) {

    HttpServletResponse response = ResponseUtil.getResponse();
    try {
      OutputStream os = response.getOutputStream();
      fileName = URLEncoder
          .encode(fileName + ExcelTypeEnum.XLS.getValue(), StandardCharsets.UTF_8.name());
      response.setContentType("application/msexcel");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader("FileName", fileName);
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

      return multipartExportExcel(os, sheetName, ExcelTypeEnum.XLS, clazz);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException("Xls导出异常");
    }
  }

  /**
   * 分段导出Xlsx至Response
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXlsx(
      String sheetName,
      Class<T> clazz) {

    return multipartExportXlsx(sheetName, sheetName, clazz);
  }

  /**
   * 分段导出Xlsx至Response
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXlsx(
      String fileName,
      String sheetName, Class<T> clazz) {

    HttpServletResponse response = ResponseUtil.getResponse();
    try (OutputStream os = response.getOutputStream()) {
      fileName = URLEncoder
          .encode(fileName + ExcelTypeEnum.XLSX.getValue(), StandardCharsets.UTF_8.name());
      response.setContentType("application/vnd.ms-excel");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader("FileName", fileName);
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

      return multipartExportExcel(os, sheetName, ExcelTypeEnum.XLSX, clazz);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException("Xls导出异常");
    }
  }

  /**
   * 分段导出Xls至文件
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXls(
      File file,
      String sheetName, Class<T> clazz) {

    return multipartExportXls(file, sheetName, sheetName, clazz);
  }

  /**
   * 分段导出Xls至文件
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXls(
      File file, String fileName,
      String sheetName, Class<T> clazz) {

    return multipartExportExcel(FileUtil.getOutputStream(file), sheetName, ExcelTypeEnum.XLS,
        clazz);
  }

  /**
   * 分段导出Xlsx至文件
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXlsx(
      File file,
      String sheetName, Class<T> clazz) {

    return multipartExportXlsx(file, sheetName, sheetName, clazz);
  }

  /**
   * 分段导出Xlsx至文件
   *
   * @param fileName
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXlsx(
      File file,
      String fileName, String sheetName, Class<T> clazz) {

    return multipartExportExcel(FileUtil.getOutputStream(file), sheetName, ExcelTypeEnum.XLSX,
        clazz);
  }

  /**
   * 导出Excel
   *
   * @param os
   * @param sheetName
   * @param excelType
   * @param clazz
   * @param datas
   * @param writeHandlers
   * @param <T>
   */
  private static <T extends ExcelModel> void exportExcel(OutputStream os, String sheetName,
      ExcelTypeEnum excelType,
      Class<T> clazz, List<T> datas, List<WriteHandler> writeHandlers) {

    ExcelMultipartWriterSheetBuilder builder = new ExcelMultipartWriterBuilder().file(os)
        .excelType(excelType).useDefaultStyle(false)
        .head(clazz).sheet(sheetName);
    writeHandlers = getWriteHandlers(writeHandlers);

    writeHandlers.forEach(builder::registerWriteHandler);

    builder.doWrite(datas);
  }

  /**
   * 分段导出Excel
   *
   * @param os
   * @param sheetName
   * @param excelType
   * @param clazz
   * @param <T>
   */
  private static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportExcel(
      OutputStream os,
      String sheetName, ExcelTypeEnum excelType, Class<T> clazz) {

    ExcelMultipartWriterSheetBuilder builder = new ExcelMultipartWriterBuilder().file(os)
        .excelType(excelType).useDefaultStyle(false)
        .head(clazz).sheet(sheetName);
    List<WriteHandler> writeHandlers = getWriteHandlers();
    writeHandlers.forEach(builder::registerWriteHandler);

    return builder;
  }

  /**
   * 获取WriteHandler
   *
   * @return
   */
  public static List<WriteHandler> getWriteHandlers() {

    return getWriteHandlers(null);
  }

  /**
   * 获取WriteHandler 如果不存在列宽策略则指定默认列宽策略
   *
   * @param writeHandlers
   * @return
   */
  public static List<WriteHandler> getWriteHandlers(List<WriteHandler> writeHandlers) {

    List<WriteHandler> retList = new ArrayList<>();
    // 默认表头样式
    retList.addAll(getDefaultStyle());

    if (CollectionUtil.isEmpty(writeHandlers)) {
      retList.add(DEFAULT_COLUMN_WIDTH_STYLE_STRATEGY);

      return retList;
    }

    retList.addAll(writeHandlers);

    if (writeHandlers.stream().anyMatch(t -> t instanceof AbstractColumnWidthStyleStrategy)) {

      return retList;
    }

    retList.add(DEFAULT_COLUMN_WIDTH_STYLE_STRATEGY);

    return retList;
  }

  private static List<WriteHandler> getDefaultStyle() {

    List<WriteHandler> handlerList = new ArrayList<>();
    WriteCellStyle headWriteCellStyle = new WriteCellStyle();
    headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
    headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
    headWriteCellStyle.setBorderTop(BorderStyle.THIN);
    headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
    headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
    headWriteCellStyle.setBorderRight(BorderStyle.THIN);
    headWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    headWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());

    WriteFont headWriteFont = new WriteFont();
    headWriteFont.setFontName("宋体");
    headWriteFont.setFontHeightInPoints((short) 11);
    headWriteFont.setBold(true);
    headWriteCellStyle.setWriteFont(headWriteFont);

    // 内容的策略
    WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
    contentWriteCellStyle.setFillPatternType(FillPatternType.NO_FILL);
    contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
    contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
    contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
    contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
    contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
    contentWriteCellStyle.setTopBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    contentWriteCellStyle.setBottomBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    contentWriteCellStyle.setLeftBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    contentWriteCellStyle.setRightBorderColor(IndexedColors.GREY_25_PERCENT.getIndex());
    WriteFont contentWriteFont = new WriteFont();

    contentWriteFont.setFontName("宋体");
    contentWriteFont.setFontHeightInPoints((short) 11);
    contentWriteCellStyle.setWriteFont(contentWriteFont);

    handlerList.add(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle));

    return handlerList;
  }
}
