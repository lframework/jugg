package com.lframework.starter.web.core.components.notify;

import com.lframework.starter.web.inner.dto.notify.SysNotifyParamsDto;

/**
 * 消息通知规则（站内信）
 */
public interface SysNotifyRuleSys extends SysNotifyRule {

  /**
   * 获取标题
   *
   * @param params
   * @return
   */
  String getTitle(SysNotifyParamsDto params);

  /**
   * 获取内容
   *
   * @param params
   * @return
   */
  String getContent(SysNotifyParamsDto params);
}
