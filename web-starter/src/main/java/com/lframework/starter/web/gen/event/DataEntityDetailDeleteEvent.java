package com.lframework.starter.web.gen.event;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;

public class DataEntityDetailDeleteEvent extends DataChangeEvent<GenDataEntityDetail> {

  public DataEntityDetailDeleteEvent(Object source, GenDataEntityDetail entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
