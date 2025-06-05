package com.lframework.starter.web.gen.listeners;

import com.lframework.starter.web.gen.event.DataEntityDeleteEvent;
import com.lframework.starter.web.gen.event.DataEntityDetailDeleteEvent;
import com.lframework.starter.web.gen.service.GenUpdateColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class UpdateColumnConfigListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private GenUpdateColumnConfigService genUpdateColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      for (String columnId : event.getColumnIds()) {
        genUpdateColumnConfigService.deleteById(columnId);
      }
    }
  }

  @Component
  public static class DeleteEntityDetailListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private GenUpdateColumnConfigService genUpdateColumnConfigService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      genUpdateColumnConfigService.deleteById(event.getId());
    }
  }
}
