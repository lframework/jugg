package com.lframework.starter.gen.listeners;

import com.lframework.starter.gen.events.DataEntityDeleteEvent;
import com.lframework.starter.gen.service.IGenerateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GenerateInfoListener implements ApplicationListener<DataEntityDeleteEvent> {

  @Autowired
  private IGenerateInfoService generateInfoService;

  @Override
  public void onApplicationEvent(DataEntityDeleteEvent event) {

    generateInfoService.deleteByDataObjId(event.getId());
  }
}
