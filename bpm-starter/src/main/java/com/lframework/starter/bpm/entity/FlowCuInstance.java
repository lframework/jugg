package com.lframework.starter.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@TableName("flow_cu_instance")
public class FlowCuInstance extends BaseEntity implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 主键
   */
  private Long id;
  /**
   * 标题
   */
  private String title;
  /**
   * 业务类型
   */
  private String bizType;

  /**
   * 业务标识
   */
  private String bizFlag;
  /**
   * 发起时间
   */
  private LocalDateTime startTime;
  /**
   * 结束时间
   */
  private LocalDateTime endTime;
}
