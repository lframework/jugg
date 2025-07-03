package com.lframework.starter.web.gen.builders;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.gen.components.custom.selector.CustomSelectorConfig;
import com.lframework.starter.web.gen.entity.GenCustomSelector;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.gen.entity.GenDataObj;
import com.lframework.starter.web.gen.entity.GenDataObjDetail;
import com.lframework.starter.web.gen.service.GenCustomSelectorService;
import com.lframework.starter.web.gen.service.GenDataEntityDetailService;
import com.lframework.starter.web.gen.service.GenDataObjDetailService;
import com.lframework.starter.web.gen.service.GenDataObjService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

public class CustomSelectorBuilder {

  @Autowired
  private GenCustomSelectorService genCustomSelectorService;

  @Autowired
  private GenDataObjService genDataObjService;

  @Autowired
  private GenDataObjDetailService genDataObjDetailService;

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Cacheable(value = CustomSelectorConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  public CustomSelectorConfig buildConfig(String id) {

    // 先查询配置信息
    GenCustomSelector data = genCustomSelectorService.findById(id);

    if (data == null) {
      throw new DefaultClientException("自定义选择器不存在！");
    }

    GenDataObj dataObj = genDataObjService.findById(data.getIdColumnRelaId());
    GenDataObjDetail dataObjDetail = genDataObjDetailService.getById(data.getIdColumnRelaId());
    GenDataEntityDetail entityDetail = genDataEntityDetailService.getById(data.getIdColumn());

    CustomSelectorConfig result = new CustomSelectorConfig();
    result.setCustomListId(data.getCustomListId());
    result.setIdColumn(
        (dataObj == null ? dataObjDetail.getSubTableAlias() : dataObj.getMainTableAlias()) + "_"
            + entityDetail.getDbColumnName());

    dataObj = genDataObjService.findById(data.getNameColumnRelaId());
    dataObjDetail = genDataObjDetailService.getById(data.getNameColumnRelaId());
    entityDetail = genDataEntityDetailService.getById(data.getNameColumn());

    result.setNameColumn(
        (dataObj == null ? dataObjDetail.getSubTableAlias() : dataObj.getMainTableAlias()) + "_"
            + entityDetail.getDbColumnName());
    result.setPlaceholder(data.getPlaceholder());
    result.setDialogTittle(data.getDialogTittle());
    result.setDialogWidth(data.getDialogWidth());

    return result;
  }
}
