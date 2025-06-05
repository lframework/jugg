package com.lframework.starter.bpm.listeners;

import com.lframework.starter.bpm.dto.FlowInstanceExtDto;
import org.dromara.warm.flow.core.listener.ListenerVariable;

public interface BpmBizListener {

  /**
   * 是否匹配
   *
   * @param ext
   * @return
   */
  boolean isMatch(FlowInstanceExtDto ext);


  /**
   * 开始监听器，任务开始办理时执行
   *
   * @param ext              业务扩展信息
   * @param listenerVariable 监听器变量
   */
  default void start(FlowInstanceExtDto ext, ListenerVariable listenerVariable) {
  }

  /**
   * 分派监听器，动态修改代办任务信息
   *
   * @param ext              业务扩展信息
   * @param listenerVariable 监听器变量
   */
  default void assignment(FlowInstanceExtDto ext, ListenerVariable listenerVariable) {
  }

  /**
   * 完成监听器，当前任务完成后执行
   *
   * @param ext                    业务扩展信息
   * @param listenerVariable       监听器变量
   * @param beforeListenerVariable 任务开始时的监听器变量
   */
  default void finish(FlowInstanceExtDto ext, ListenerVariable listenerVariable,
      ListenerVariable beforeListenerVariable) {
  }

  /**
   * 业务完成监听器，流程完成时执行
   *
   * @param businessId
   * @param startById
   */
  default void businessComplete(ListenerVariable listenerVariable, String businessId,
      String startById) {

  }

  /**
   * 创建监听器，任务创建时执行
   *
   * @param ext              业务扩展信息
   * @param listenerVariable 监听器变量
   */
  default void create(FlowInstanceExtDto ext, ListenerVariable listenerVariable) {
  }
}
