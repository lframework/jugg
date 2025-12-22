package com.lframework.starter.web.inner.events.system;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.inner.entity.SysDept;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteSysDeptEvent extends DataChangeEvent<SysDept> {

  public DeleteSysDeptEvent(Object source, SysDept entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
