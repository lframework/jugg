package com.lframework.starter.web.inner.events.system;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.inner.entity.SysRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSysRoleEvent extends DataChangeEvent<SysRole> {

  public DeleteSysRoleEvent(Object source, SysRole entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
