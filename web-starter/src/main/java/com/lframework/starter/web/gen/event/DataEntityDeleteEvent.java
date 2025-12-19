package com.lframework.starter.web.gen.event;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.gen.entity.GenDataEntity;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

public class DataEntityDeleteEvent extends DataChangeEvent<GenDataEntity> {

  @Getter
  @Setter
  private List<String> columnIds;

  public DataEntityDeleteEvent(Object source, GenDataEntity entity,
      DataChangeType type) {
    super(source, entity, type);
  }
}
