package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataObjectDeleteEvent;
import com.lframework.starter.gen.service.IGenCreateColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CreateColumnConfigListener implements ApplicationListener<DataObjectDeleteEvent> {

  @Autowired
  private IGenCreateColumnConfigService genCreateColumnConfigService;

  @Override
  public void onApplicationEvent(DataObjectDeleteEvent event) {

    for (String columnId : event.getColumnIds()) {
      genCreateColumnConfigService.deleteById(columnId);
    }
  }
}
