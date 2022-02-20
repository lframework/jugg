package com.lframework.gen.listeners;

import com.lframework.gen.events.DataObjectDeleteEvent;
import com.lframework.gen.service.IGenDetailColumnConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DetailColumnConfigListener implements ApplicationListener<DataObjectDeleteEvent> {

    @Autowired
    private IGenDetailColumnConfigService genDetailColumnConfigService;

    @Override
    public void onApplicationEvent(DataObjectDeleteEvent event) {

        for (String columnId : event.getColumnIds()) {
            genDetailColumnConfigService.deleteById(columnId);
        }
    }
}
