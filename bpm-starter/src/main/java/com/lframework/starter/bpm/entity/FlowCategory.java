package com.lframework.starter.bpm.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.io.Serializable;
import lombok.Data;

@Data
@TableName("flow_cu_category")
public class FlowCategory extends BaseEntity implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "FlowCategory";

  /**
   * 主键
   */
  private String id;
  /**
   * 流程类型名称
   */
  private String name;
  /**
   * 父节点ID
   */
  private String parentId;
}
