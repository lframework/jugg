package com.lframework.starter.web.inner.events.system;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.inner.entity.SysNotifyGroup;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteSysNotifyGroupEvent extends DataChangeEvent<SysNotifyGroup> {

  public DeleteSysNotifyGroupEvent(Object source, SysNotifyGroup entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
