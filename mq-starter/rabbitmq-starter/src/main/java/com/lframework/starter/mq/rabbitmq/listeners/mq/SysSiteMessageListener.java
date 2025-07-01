package com.lframework.starter.mq.rabbitmq.listeners.mq;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mq.rabbitmq.constants.RabbitMqStringPool;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.dto.message.SysSiteMessageDto;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.system.SysSiteMessageService;
import com.lframework.starter.web.inner.service.system.SysUserService;
import com.lframework.starter.web.websocket.components.WsDataPusher;
import com.lframework.starter.web.websocket.dto.WsPushData;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SysSiteMessageListener {

  @Autowired
  private SysSiteMessageService sysSiteMessageService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private WsDataPusher wsDataPusher;

  @RabbitListener(bindings = {
      @QueueBinding(value = @Queue(value = RabbitMqStringPool.SYS_SITE_MESSAGE_QUEUE), key = RabbitMqStringPool.SYS_SITE_MESSAGE_ROUTING_KEY, exchange = @Exchange(value = RabbitMqStringPool.SYS_SITE_MESSAGE_EXCHANGE))})
  public void execute(Message<SysSiteMessageDto> message) {

    SysSiteMessageDto dto = message.getPayload();
    log.info("接收到发送站内信消息 {}", dto);

    if (CollectionUtil.isEmpty(dto.getUserIdList())) {
      log.info("接收人为空，不发送");
      return;
    }

    String title = dto.getTitle();
    String content = dto.getContent();
    if (StringUtil.isBlank(title) || StringUtil.isBlank(content) || StringUtil.isBlank(
        dto.getBizKey())) {
      log.info("标题、内容、业务键不能为空，不发送");
      return;
    }

    SysUser createBy = StringUtil.isBlank(dto.getCreateUserId()) ? null
        : sysUserService.findById(dto.getCreateUserId());

    List<SysSiteMessage> recordList = dto.getUserIdList().stream().distinct().map(t -> {
      SysSiteMessage record = new SysSiteMessage();
      record.setId(IdUtil.getId());
      record.setTitle(title);
      record.setContent(content);
      record.setReceiverId(t);
      record.setBizKey(dto.getBizKey());
      if (createBy != null) {
        record.setCreateById(createBy.getId());
        record.setCreateBy(createBy.getName());
        record.setUpdateBy(createBy.getName());
        record.setUpdateById(createBy.getId());
      }

      return record;
    }).collect(Collectors.toList());

    sysSiteMessageService.saveBatch(recordList);

    for (SysSiteMessage sysSiteMessage : recordList) {
      try {
        WsPushData pushData = new WsPushData();
        pushData.setBizType("siteMessage");
        pushData.setIncludeUserId(sysSiteMessage.getReceiverId());
        wsDataPusher.push(pushData);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
      }
    }
  }
}
