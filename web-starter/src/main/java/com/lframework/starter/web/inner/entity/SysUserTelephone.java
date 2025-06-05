package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("sys_user_telephone")
public class SysUserTelephone extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 手机号
   */
  private String telephone;

  /**
   * 用户ID
   */
  private String userId;
}
