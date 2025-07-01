package com.lframework.starter.web.gen.listeners;

import com.lframework.starter.web.gen.event.DataEntityDeleteEvent;
import com.lframework.starter.web.gen.event.DataEntityDetailDeleteEvent;
import com.lframework.starter.web.gen.service.GenDetailColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class DetailColumnConfigListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private GenDetailColumnConfigService genDetailColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      for (String columnId : event.getColumnIds()) {
        genDetailColumnConfigService.deleteById(columnId);
      }
    }
  }

  @Component
  public static class DeleteEntityDetailListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private GenDetailColumnConfigService genDetailColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      genDetailColumnConfigService.deleteById(event.getId());
    }
  }
}
