package com.lframework.starter.web.gen.listeners;

import com.lframework.starter.web.gen.event.DataEntityDeleteEvent;
import com.lframework.starter.web.gen.service.GenerateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class GenerateInfoListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private GenerateInfoService generateInfoService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      generateInfoService.deleteByEntityId(event.getId());
    }
  }
}
