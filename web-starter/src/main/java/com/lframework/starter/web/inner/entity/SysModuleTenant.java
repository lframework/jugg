package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 租户和系统模块关系表
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_module_tenant")
public class SysModuleTenant extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "SysModuleTenant";
  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 模块ID
   */
  private Integer moduleId;

  /**
   * 租户ID
   */
  private Integer tenantId;

  /**
   * 过期时间
   */
  private LocalDateTime expireTime;
}
