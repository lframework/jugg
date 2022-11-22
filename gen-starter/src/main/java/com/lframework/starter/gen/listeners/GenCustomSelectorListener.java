package com.lframework.starter.gen.listeners;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.gen.entity.GenCustomSelector;
import com.lframework.starter.gen.events.CustomListDeleteEvent;
import com.lframework.starter.gen.service.IGenCustomSelectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

public class GenCustomSelectorListener {

  @Component
  public static class CustomListDeleteListener implements
      ApplicationListener<CustomListDeleteEvent> {

    @Autowired
    private IGenCustomSelectorService genCustomSelectorService;

    @Override
    public void onApplicationEvent(CustomListDeleteEvent event) {

      Wrapper<GenCustomSelector> queryWrapper = Wrappers.lambdaQuery(GenCustomSelector.class)
          .eq(GenCustomSelector::getCustomListId, event.getId());
      if (genCustomSelectorService.count(queryWrapper) > 0) {
        throw new DefaultClientException("自定义列表【" + event.getName() + "】已关联自定义选择器，无法删除！");
      }
    }
  }
}
