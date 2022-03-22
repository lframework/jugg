package com.lframework.gen.listeners;

import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.service.IGenerateInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class GenerateInfoListener implements ApplicationListener<DataObjectDeleteEvent> {

  @Autowired
  private IGenerateInfoService generateInfoService;

  @Override
  public void onApplicationEvent(DataObjectDeleteEvent event) {

    generateInfoService.deleteByDataObjId(event.getId());
  }
}
