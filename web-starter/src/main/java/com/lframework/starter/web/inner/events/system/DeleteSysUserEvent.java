package com.lframework.starter.web.inner.events.system;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.inner.entity.SysUser;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSysUserEvent extends DataChangeEvent<SysUser> {

  public DeleteSysUserEvent(Object source, SysUser entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
