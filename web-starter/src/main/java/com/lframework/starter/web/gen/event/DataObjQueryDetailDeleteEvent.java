package com.lframework.starter.web.gen.event;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.gen.entity.GenDataObjQueryDetail;

public class DataObjQueryDetailDeleteEvent extends DataChangeEvent<GenDataObjQueryDetail> {

  public DataObjQueryDetailDeleteEvent(Object source, GenDataObjQueryDetail entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
