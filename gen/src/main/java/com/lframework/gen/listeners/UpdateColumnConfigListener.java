package com.lframework.gen.listeners;

import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.service.IGenUpdateColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UpdateColumnConfigListener implements ApplicationListener<DataObjectDeleteEvent> {

    @Autowired
    private IGenUpdateColumnConfigService genUpdateColumnConfigService;

    @Override
    public void onApplicationEvent(DataObjectDeleteEvent event) {

        for (String columnId : event.getColumnIds()) {
            genUpdateColumnConfigService.deleteById(columnId);
        }
    }
}
