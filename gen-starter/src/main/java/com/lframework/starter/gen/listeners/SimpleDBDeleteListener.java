package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.events.DataEntityDetailDeleteEvent;
import com.lframework.starter.gen.service.ISimpleTableColumnService;
import com.lframework.starter.gen.service.ISimpleTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class SimpleDBDeleteListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private ISimpleTableService simpleTableService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      String id = event.getId();

      simpleTableService.deleteByEntityId(id);
    }
  }

  @Component
  public static class DeleteEntityDetailListener implements
      ApplicationListener<DataEntityDetailDeleteEvent> {

    @Autowired
    private ISimpleTableColumnService simpleTableColumnService;

    @Override
    public void onApplicationEvent(DataEntityDetailDeleteEvent event) {

      String id = event.getId();

      simpleTableColumnService.removeById(id);
    }
  }
}
