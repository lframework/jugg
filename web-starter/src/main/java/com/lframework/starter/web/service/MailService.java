package com.lframework.starter.web.service;

import java.io.File;
import java.util.List;

public interface MailService extends BaseService {

  /**
   * 发送邮件
   *
   * @param to      收件人
   * @param subject 标题
   * @param content 正文
   * @return message-id
   */
  String send(String to, String subject, String content);

  /**
   * 发送邮件
   *
   * @param to      收件人
   * @param subject 标题
   * @param content 正文
   * @param isHtml  – 是否为HTML格式
   * @return message-id
   */
  String send(String to, String subject, String content, boolean isHtml);

  /**
   * 发送邮件
   *
   * @param to      收件人
   * @param subject 标题
   * @param content 正文
   * @param isHtml  – 是否为HTML格式
   * @param files   – 附件列表
   * @return message-id
   */
  String send(String to, String subject, String content, boolean isHtml, File... files);

  /**
   * 发送邮件
   *
   * @param tos     收件人
   * @param subject 标题
   * @param content 正文
   * @return message-id
   */
  String send(List<String> tos, String subject, String content);

  /**
   * 发送邮件
   *
   * @param tos     收件人
   * @param subject 标题
   * @param content 正文
   * @param isHtml  – 是否为HTML格式
   * @return message-id
   */
  String send(List<String> tos, String subject, String content, boolean isHtml);

  /**
   * 发送邮件
   *
   * @param tos     收件人
   * @param subject 标题
   * @param content 正文
   * @param isHtml  – 是否为HTML格式
   * @param files   – 附件列表
   * @return message-id
   */
  String send(List<String> tos, String subject, String content, boolean isHtml, File... files);
}
