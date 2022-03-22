package com.lframework.gen.listeners;

import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.service.ISimpleTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleDBDeleteListener implements ApplicationListener<DataObjectDeleteEvent> {

  @Autowired
  private ISimpleTableService simpleTableService;

  @Override
  public void onApplicationEvent(DataObjectDeleteEvent event) {

    String id = event.getId();

    simpleTableService.deleteByDataObjId(id);
  }
}
