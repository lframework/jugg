package com.lframework.gen.listeners;

import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.service.IGenQueryParamsColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class QueryParamsColumnConfigListener implements ApplicationListener<DataObjectDeleteEvent> {

    @Autowired
    private IGenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

    @Override
    public void onApplicationEvent(DataObjectDeleteEvent event) {

        for (String columnId : event.getColumnIds()) {
            genQueryParamsColumnConfigService.deleteById(columnId);
        }
    }
}
