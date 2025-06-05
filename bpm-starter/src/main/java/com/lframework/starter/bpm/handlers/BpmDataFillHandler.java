package com.lframework.starter.bpm.handlers;

import com.lframework.starter.web.core.utils.IdUtil;
import java.util.Objects;
import org.dromara.warm.flow.core.entity.RootEntity;
import org.dromara.warm.flow.core.handler.DataFillHandler;
import org.dromara.warm.flow.core.utils.ObjectUtil;

public class BpmDataFillHandler implements DataFillHandler {

  @Override
  public void idFill(Object object) {
    RootEntity entity = (RootEntity) object;
    if (ObjectUtil.isNotNull(entity)) {
      if (Objects.isNull(entity.getId())) {
        entity.setId(IdUtil.getIdLong());
      }
    }
  }
}
