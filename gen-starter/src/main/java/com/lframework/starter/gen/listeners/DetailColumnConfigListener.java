package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.service.IGenDetailColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DetailColumnConfigListener implements ApplicationListener<DataEntityDeleteEvent> {

  @Autowired
  private IGenDetailColumnConfigService genDetailColumnConfigService;

  @Override
  public void onApplicationEvent(DataEntityDeleteEvent event) {

    for (String columnId : event.getColumnIds()) {
      genDetailColumnConfigService.deleteById(columnId);
    }
  }
}
