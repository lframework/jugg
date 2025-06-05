package com.lframework.starter.mq.rabbitmq.listeners.app;

import com.lframework.starter.web.websocket.dto.WsPushData;
import com.lframework.starter.web.websocket.components.WsDataPusher;
import com.lframework.starter.web.websocket.events.UserConnectEvent;
import com.lframework.starter.mq.core.events.ExportTaskNotifyEvent;
import com.lframework.starter.mq.core.service.ExportTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
public class ExportTaskNotifyListener implements ApplicationListener<UserConnectEvent> {

  @Autowired
  private WsDataPusher wsDataPusher;

  @Autowired
  private ExportTaskService exportTaskService;

  @TransactionalEventListener
  public void execute(ExportTaskNotifyEvent event) {
    this.notify(event.getCreateById());
  }

  @Override
  public void onApplicationEvent(UserConnectEvent event) {
    this.notify(event.getUser().getId());
  }

  private void notify(String userId) {
    // 发送广播
    WsPushData pushData = new WsPushData();
    pushData.setBizType("exportTask");
    pushData.setDataObj(exportTaskService.getSummaryByUserId(userId));
    pushData.setIncludeUserId(userId);

    wsDataPusher.push(pushData);
  }
}
