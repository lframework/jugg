package com.lframework.starter.web.components.excel;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.builder.AbstractExcelWriterParameterBuilder;
import com.alibaba.excel.write.metadata.WriteWorkbook;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 可分段写入Excel的ExcelWriterBuilder
 *
 * @author zmj
 */
public class ExcelMultipartWriterBuilder
        extends AbstractExcelWriterParameterBuilder<ExcelMultipartWriterBuilder, WriteWorkbook> {

    /**
     * Workbook
     */
    private WriteWorkbook writeWorkbook;

    public ExcelMultipartWriterBuilder() {

        this.writeWorkbook = new WriteWorkbook();
    }

    /**
     * Default true
     *
     * @param autoCloseStream
     * @return
     */
    public ExcelMultipartWriterBuilder autoCloseStream(Boolean autoCloseStream) {

        writeWorkbook.setAutoCloseStream(autoCloseStream);
        return this;
    }

    /**
     * Whether the encryption.
     * <p>
     * WARRING:Encryption is when the entire file is read into memory, so it is very memory intensive.
     *
     * @param password
     * @return
     */
    public ExcelMultipartWriterBuilder password(String password) {

        writeWorkbook.setPassword(password);
        return this;
    }

    /**
     * Write excel in memory. Default false,the cache file is created and finally written to excel.
     * <p>
     * Comment and RichTextString are only supported in memory mode.
     */
    public ExcelMultipartWriterBuilder inMemory(Boolean inMemory) {

        writeWorkbook.setInMemory(inMemory);
        return this;
    }

    /**
     * Excel is also written in the event of an exception being thrown.The default false.
     */
    public ExcelMultipartWriterBuilder writeExcelOnException(Boolean writeExcelOnException) {

        writeWorkbook.setWriteExcelOnException(writeExcelOnException);
        return this;
    }

    /**
     * The default is all excel objects.if true , you can use {@link com.alibaba.excel.annotation.ExcelIgnore} ignore a
     * field. if false , you must use {@link com.alibaba.excel.annotation.ExcelProperty} to use a filed.
     * <p>
     * Default true
     *
     * @param convertAllFiled
     * @return
     * @deprecated Just to be compatible with historical data, The default is always going to be convert all filed.
     */
    @Deprecated
    public ExcelMultipartWriterBuilder convertAllFiled(Boolean convertAllFiled) {

        writeWorkbook.setConvertAllFiled(convertAllFiled);
        return this;
    }

    public ExcelMultipartWriterBuilder excelType(ExcelTypeEnum excelType) {

        writeWorkbook.setExcelType(excelType);
        return this;
    }

    public ExcelMultipartWriterBuilder file(OutputStream outputStream) {

        writeWorkbook.setOutputStream(outputStream);
        return this;
    }

    public ExcelMultipartWriterBuilder file(File outputFile) {

        writeWorkbook.setFile(outputFile);
        return this;
    }

    public ExcelMultipartWriterBuilder file(String outputPathName) {

        return file(new File(outputPathName));
    }

    public ExcelMultipartWriterBuilder withTemplate(InputStream templateInputStream) {

        writeWorkbook.setTemplateInputStream(templateInputStream);
        return this;
    }

    public ExcelMultipartWriterBuilder withTemplate(File templateFile) {

        writeWorkbook.setTemplateFile(templateFile);
        return this;
    }

    public ExcelMultipartWriterBuilder withTemplate(String pathName) {

        return withTemplate(new File(pathName));
    }

    /**
     * Write handler
     *
     * @deprecated please use {@link WriteHandler}
     */
    @Deprecated
    public ExcelMultipartWriterBuilder registerWriteHandler(com.alibaba.excel.event.WriteHandler writeHandler) {

        writeWorkbook.setWriteHandler(writeHandler);
        return this;
    }

    public ExcelWriter build() {

        return new ExcelWriter(writeWorkbook);
    }

    public ExcelMultipartWriterSheetBuilder sheet() {

        return sheet(null, null);
    }

    public ExcelMultipartWriterSheetBuilder sheet(Integer sheetNo) {

        return sheet(sheetNo, null);
    }

    public ExcelMultipartWriterSheetBuilder sheet(String sheetName) {

        return sheet(null, sheetName);
    }

    public ExcelMultipartWriterSheetBuilder sheet(Integer sheetNo, String sheetName) {

        ExcelWriter excelWriter = build();
        ExcelMultipartWriterSheetBuilder ExcelMultipartWriterSheetBuilder = new ExcelMultipartWriterSheetBuilder(
                excelWriter);
        if (sheetNo != null) {
            ExcelMultipartWriterSheetBuilder.sheetNo(sheetNo);
        }
        if (sheetName != null) {
            ExcelMultipartWriterSheetBuilder.sheetName(sheetName);
        }
        return ExcelMultipartWriterSheetBuilder;
    }

    @Override
    protected WriteWorkbook parameter() {

        return writeWorkbook;
    }
}
