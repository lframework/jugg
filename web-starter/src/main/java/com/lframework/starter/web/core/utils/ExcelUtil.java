package com.lframework.starter.web.core.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.FileUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.web.core.annotations.excel.ExcelRequired;
import com.lframework.starter.web.core.components.excel.ExcelHorizontalCellStyleStrategy;
import com.lframework.starter.web.core.components.excel.ExcelModel;
import com.lframework.starter.web.core.components.excel.ExcelMultipartWriterBuilder;
import com.lframework.starter.web.core.components.excel.ExcelMultipartWriterSheetBuilder;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.multipart.MultipartFile;

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
   * 读取Excel
   *
   * @param file
   * @param listener
   */
  public static <T> ExcelReaderBuilder read(MultipartFile file, Class<T> clazz,
      ReadListener<T> listener) {
    try {
      return EasyExcel.read(file.getInputStream(), clazz, listener);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException(e.getMessage());
    }
  }

  /**
   * 导出Xls至Response
   *
   * @param sheetName
   * @param clazz
   * @param <T>
   */
  public static <T extends ExcelModel> void exportXls(String sheetName, Class<T> clazz) {

    exportXls(sheetName, sheetName, clazz, CollectionUtil.emptyList(), null);
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

    exportXls(fileName, sheetName, clazz, CollectionUtil.emptyList(), null);
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
      fileName = URLEncoder.encode(fileName + ExcelTypeEnum.XLS.getValue(),
          StandardCharsets.UTF_8.name());
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

    exportXlsx(sheetName, sheetName, clazz, CollectionUtil.emptyList(), null);
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

    exportXlsx(fileName, sheetName, clazz, CollectionUtil.emptyList(), null);
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
      fileName = URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(),
          StandardCharsets.UTF_8.name());
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

    exportXls(file, sheetName, clazz, CollectionUtil.emptyList(), null);
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

    exportXlsx(file, sheetName, clazz, CollectionUtil.emptyList(), null);
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
   * @param head
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXls(
      String sheetName,
      List<String> head) {

    return multipartExportXls(sheetName, sheetName, head);
  }

  /**
   * 分段导出Xls至Response
   *
   * @param fileName
   * @param sheetName
   * @param head
   * @param <T>
   */
  public static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportXls(
      String fileName,
      String sheetName, List<String> head) {

    HttpServletResponse response = ResponseUtil.getResponse();
    try {
      OutputStream os = response.getOutputStream();
      fileName = URLEncoder.encode(fileName + ExcelTypeEnum.XLS.getValue(),
          StandardCharsets.UTF_8.name());
      response.setContentType("application/msexcel");
      response.setCharacterEncoding(StandardCharsets.UTF_8.name());
      response.setHeader("FileName", fileName);
      response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

      return multipartExportExcel(os, sheetName, ExcelTypeEnum.XLS, head);
    } catch (IOException e) {
      log.error(e.getMessage(), e);
      throw new DefaultSysException("Xls导出异常");
    }
  }

  /**
   * 分段导出Excel
   *
   * @param os
   * @param sheetName
   * @param excelType
   * @param head
   * @param <T>
   */
  private static <T extends ExcelModel> ExcelMultipartWriterSheetBuilder multipartExportExcel(
      OutputStream os,
      String sheetName, ExcelTypeEnum excelType, List<String> head) {

    List<List<String>> headWrapper = new ArrayList<>();
    if (!CollectionUtil.isEmpty(head)) {
      for (String s : head) {
        headWrapper.add(Collections.singletonList(s));
      }
    }

    ExcelMultipartWriterSheetBuilder builder = new ExcelMultipartWriterBuilder().file(os)
        .excelType(excelType)
        .useDefaultStyle(false).head(headWrapper).sheet(sheetName);
    List<WriteHandler> writeHandlers = getWriteHandlers();
    writeHandlers.forEach(builder::registerWriteHandler);

    return builder;
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
      fileName = URLEncoder.encode(fileName + ExcelTypeEnum.XLS.getValue(),
          StandardCharsets.UTF_8.name());
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
      fileName = URLEncoder.encode(fileName + ExcelTypeEnum.XLSX.getValue(),
          StandardCharsets.UTF_8.name());
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
        .excelType(excelType)
        .useDefaultStyle(false).head(clazz).sheet(sheetName);
    writeHandlers = getWriteHandlers(writeHandlers, clazz);

    writeHandlers.forEach(builder::registerWriteHandler);

    builder.doWrite(datas);
    builder.finish();
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
        .excelType(excelType)
        .useDefaultStyle(false).head(clazz).sheet(sheetName);
    List<WriteHandler> writeHandlers = getWriteHandlers(null, clazz);
    writeHandlers.forEach(builder::registerWriteHandler);

    return builder;
  }

  /**
   * 获取WriteHandler
   *
   * @return
   */
  public static List<WriteHandler> getWriteHandlers() {

    return getWriteHandlers(null, null);
  }

  /**
   * 获取WriteHandler 如果不存在列宽策略则指定默认列宽策略
   *
   * @param writeHandlers
   * @return
   */
  public static List<WriteHandler> getWriteHandlers(List<WriteHandler> writeHandlers,
      Class headClass) {

    List<WriteHandler> retList = new ArrayList<>();
    // 默认表头样式
    retList.addAll(getDefaultStyle(getRequiredFieldNames(headClass)));

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

  private static List<WriteHandler> getDefaultStyle(Set<String> requiredFiledNames) {

    List<WriteHandler> handlerList = new ArrayList<>();

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

    handlerList.add(new ExcelHorizontalCellStyleStrategy(getHeadStyle(false), getHeadStyle(true),
        contentWriteCellStyle, requiredFiledNames));

    return handlerList;
  }

  private static WriteCellStyle getHeadStyle(boolean isRequiredField) {
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
    if (isRequiredField) {
      headWriteFont.setColor(Font.COLOR_RED);
    }
    headWriteCellStyle.setWriteFont(headWriteFont);

    return headWriteCellStyle;
  }

  private static Set<String> getRequiredFieldNames(Class headClass) {
    if (headClass == null) {
      return null;
    }

    Field[] fields = ReflectUtil.getFields(headClass, t ->
        t.getAnnotation(ExcelRequired.class) != null);
    if (ArrayUtil.isEmpty(fields)) {
      return null;
    }

    Set<String> result = new HashSet<>();
    for (Field field : fields) {
      WriteCellStyle headWriteCellStyle = new WriteCellStyle();

      WriteFont headWriteFont = new WriteFont();
      headWriteCellStyle.setWriteFont(headWriteFont);
      result.add(field.getName());
    }

    return result;
  }
}
