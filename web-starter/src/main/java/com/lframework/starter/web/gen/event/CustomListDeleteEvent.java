package com.lframework.starter.web.gen.event;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.gen.entity.GenCustomList;

public class CustomListDeleteEvent extends DataChangeEvent<GenCustomList> {

  public CustomListDeleteEvent(Object source, GenCustomList entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
