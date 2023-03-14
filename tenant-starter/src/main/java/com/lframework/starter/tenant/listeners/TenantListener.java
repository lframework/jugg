package com.lframework.starter.tenant.listeners;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.lframework.starter.web.common.event.ClearTenantEvent;
import com.lframework.starter.web.common.event.SetTenantEvent;
import org.springframework.context.ApplicationListener;

public class TenantListener {

  public static class SetTenantListener implements ApplicationListener<SetTenantEvent> {

    @Override
    public void onApplicationEvent(SetTenantEvent event) {
      DynamicDataSourceContextHolder.push(String.valueOf(event.getTenantId()));
    }
  }

  public static class ClearTenantListener implements ApplicationListener<ClearTenantEvent> {

    @Override
    public void onApplicationEvent(ClearTenantEvent event) {
      DynamicDataSourceContextHolder.clear();
    }
  }
}
