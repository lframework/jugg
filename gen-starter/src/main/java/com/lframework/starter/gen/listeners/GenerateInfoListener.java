package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.service.IGenerateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class GenerateInfoListener {

  @Component
  public static class DeleteEntityListener implements ApplicationListener<DataEntityDeleteEvent> {

    @Autowired
    private IGenerateInfoService generateInfoService;

    @Override
    public void onApplicationEvent(DataEntityDeleteEvent event) {

      generateInfoService.deleteByEntityId(event.getId());
    }
  }
}
