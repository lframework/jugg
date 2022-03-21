package com.lframework.starter.web.impl;

import cn.hutool.extra.mail.MailAccount;
import com.lframework.common.utils.MailUtil;
import com.lframework.starter.web.service.IMailService;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Collections;
import java.util.List;

@Slf4j
public class MailServiceImpl implements IMailService {

    private MailAccount account;

    public MailServiceImpl(MailAccount account) {
        this.account = account;
    }

    @Override
    public String send(String to, String subject, String content) {
        return this.send(Collections.singletonList(to), subject, content, false, null);
    }

    @Override
    public String send(String to, String subject, String content, boolean isHtml) {
        return this.send(Collections.singletonList(to), subject, content, isHtml, null);
    }

    @Override
    public String send(String to, String subject, String content, boolean isHtml, File... files) {
        return this.send(Collections.singletonList(to), subject, content, isHtml, files);
    }

    @Override
    public String send(List<String> tos, String subject, String content) {
        return this.send(tos, subject, content, false, null);
    }

    @Override
    public String send(List<String> tos, String subject, String content, boolean isHtml) {
        return this.send(tos, subject, content, isHtml, null);
    }

    @Override
    public String send(List<String> tos, String subject, String content, boolean isHtml, File... files) {

        log.info("开始发送邮件");
        if (log.isDebugEnabled()) {
            log.debug("收件人={}, 标题={}, 内容={}, isHtml={}, files={}", tos, subject, content, isHtml, files);
        }

        return MailUtil.send(this.account, tos, subject, content, isHtml, files);
    }
}
