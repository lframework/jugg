package com.lframework.starter.mq.rabbitmq.constants;

public interface RabbitMqStringPool {

  // 增加库存
  String ADD_STOCK_EXCHANGE = "add_stock.fanout";

  // 扣减库存
  String SUB_STOCK_EXCHANGE = "sub_stock.fanout";

  // 审核通过订单
  String APPROVE_PASS_ORDER_EXCHANGE = "approve_pass_order.fanout";

  // 消息通知
  String SYS_NOTIFY_EXCHANGE = "sys_notify.direct";
  String SYS_NOTIFY_ROUTING_KEY = "sys_notify_routing_key";
  String SYS_NOTIFY_QUEUE = "sys_notify";

  // 邮件消息
  String SYS_MAIL_MESSAGE_EXCHANGE = "sys_mail_message.direct";
  String SYS_MAIL_MESSAGE_ROUTING_KEY = "sys_mail_message_routing_key";
  String SYS_MAIL_MESSAGE_QUEUE = "sys_mail_message";

  // 站内信
  String SYS_SITE_MESSAGE_EXCHANGE = "sys_site_message.direct";
  String SYS_SITE_MESSAGE_ROUTING_KEY = "sys_site_message_routing_key";
  String SYS_SITE_MESSAGE_QUEUE = "sys_site_message";

  // 新增导出任务
  String ADD_EXPORT_TASK_EXCHANGE = "add_export_task.direct";
  String ADD_EXPORT_TASK_ROUTING_KEY = "add_export_task_routing_key";
  String ADD_EXPORT_TASK_QUEUE = "add_export_task";

  // 执行导出任务
  String EXECUTE_EXPORT_TASK_EXCHANGE = "execute_export_task.direct";
  String EXECUTE_EXPORT_TASK_ROUTING_KEY = "execute_export_task_routing_key";
  String EXECUTE_EXPORT_TASK_QUEUE = "execute_export_task";

}
