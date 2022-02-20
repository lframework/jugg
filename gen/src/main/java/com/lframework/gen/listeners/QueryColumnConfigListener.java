package com.lframework.gen.listeners;

import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.service.IGenQueryColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QueryColumnConfigListener implements ApplicationListener<DataObjectDeleteEvent> {

    @Autowired
    private IGenQueryColumnConfigService genQueryColumnConfigService;

    @Override
    public void onApplicationEvent(DataObjectDeleteEvent event) {

        for (String columnId : event.getColumnIds()) {
            genQueryColumnConfigService.deleteById(columnId);
        }
    }
}
