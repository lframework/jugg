package com.lframework.starter.web.utils;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.bo.BasePrintBo;
import com.lframework.starter.web.bo.BasePrintDataBo;
import com.lframework.starter.web.dto.BaseDto;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.Map;

@Slf4j
public class PrintUtil {

    /**
     * 获取freeMarker Template
     *
     * @param templateName
     * @return
     */
    private static Template getTemplate(String templateName) {

        Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
        cfg.setClassForTemplateLoading(PrintUtil.class, "/");
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        try {
            return cfg.getTemplate(templateName);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    /**
     * 生成打印模板的html
     *
     * @param templateName 模板名称
     * @param data         数据
     * @return
     */
    public static <T extends BasePrintDataBo<? extends BaseDto>> String generate(String templateName, T data) {

        Template template = getTemplate(templateName);
        Map root = data == null ? Collections.EMPTY_MAP : JsonUtil.convert(data, Map.class);
        StringWriter stringWriter = new StringWriter();
        BufferedWriter writer = new BufferedWriter(stringWriter);

        try {
            template.process(root, writer);
        } catch (TemplateException | IOException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }

        return stringWriter.toString();
    }
}
