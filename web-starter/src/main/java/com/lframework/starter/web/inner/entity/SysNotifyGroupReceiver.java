package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 * 消息通知组-接收者关系表
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_notify_group_receiver")
public class SysNotifyGroupReceiver extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 消息通知组ID
   */
  private String groupId;

  /**
   * 接收者ID
   */
  private String receiverId;
}
