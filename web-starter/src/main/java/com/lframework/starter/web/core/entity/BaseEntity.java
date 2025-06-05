package com.lframework.starter.web.core.entity;

import java.io.Serializable;
import lombok.Data;

/**
 * 直接使用BaseEntity insert/update时 成员变量会自动赋值（如果有值，不会覆盖）
 *
 * @author zmj
 */
@Data
public abstract class BaseEntity implements Serializable {

  private static final long serialVersionUID = 1L;
}
