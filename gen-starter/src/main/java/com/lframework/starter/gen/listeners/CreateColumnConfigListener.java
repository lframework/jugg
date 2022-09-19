package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.service.IGenCreateColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class CreateColumnConfigListener implements ApplicationListener<DataEntityDeleteEvent> {

  @Autowired
  private IGenCreateColumnConfigService genCreateColumnConfigService;

  @Override
  public void onApplicationEvent(DataEntityDeleteEvent event) {

    for (String columnId : event.getColumnIds()) {
      genCreateColumnConfigService.deleteById(columnId);
    }
  }
}
