package com.lframework.starter.web.inner.events.system;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.inner.entity.SysUserGroup;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteSysUserGroupEvent extends DataChangeEvent<SysUserGroup> {

  public DeleteSysUserGroupEvent(Object source, SysUserGroup entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
