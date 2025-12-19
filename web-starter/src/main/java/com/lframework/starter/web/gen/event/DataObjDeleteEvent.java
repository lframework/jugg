package com.lframework.starter.web.gen.event;

import com.lframework.starter.web.core.event.DataChangeEvent;
import com.lframework.starter.web.core.event.DataChangeType;
import com.lframework.starter.web.gen.entity.GenDataObj;
import java.util.List;

public class DataObjDeleteEvent extends DataChangeEvent<GenDataObj> {

  private List<String> detailIds;

  private List<String> queryDetailIds;

  public DataObjDeleteEvent(Object source, GenDataObj entity,
      DataChangeType type) {
    super(source, entity, type);
  }

  public List<String> getDetailIds() {
    return detailIds;
  }

  public void setDetailIds(List<String> detailIds) {
    this.detailIds = detailIds;
  }

  public List<String> getQueryDetailIds() {
    return queryDetailIds;
  }

  public void setQueryDetailIds(List<String> queryDetailIds) {
    this.queryDetailIds = queryDetailIds;
  }
}
