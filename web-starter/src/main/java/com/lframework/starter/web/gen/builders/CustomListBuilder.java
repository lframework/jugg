package com.lframework.starter.web.gen.builders;

import cn.hutool.core.convert.Convert;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.gen.components.custom.list.CustomListConfig;
import com.lframework.starter.web.gen.components.custom.list.CustomListConfig.FieldConfig;
import com.lframework.starter.web.gen.components.custom.list.CustomListConfig.HandleColumn;
import com.lframework.starter.web.gen.components.custom.list.CustomListConfig.ListConfig;
import com.lframework.starter.web.gen.components.custom.list.CustomListConfig.QueryParam;
import com.lframework.starter.web.gen.components.custom.list.CustomListConfig.Toolbar;
import com.lframework.starter.web.gen.components.data.obj.DataObjectQueryObj;
import com.lframework.starter.web.gen.components.data.obj.DataObjectQueryParamObj;
import com.lframework.starter.web.gen.entity.GenCustomList;
import com.lframework.starter.web.gen.entity.GenCustomListDetail;
import com.lframework.starter.web.gen.entity.GenCustomListHandleColumn;
import com.lframework.starter.web.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.web.gen.entity.GenCustomListToolbar;
import com.lframework.starter.web.gen.entity.GenCustomSelector;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.gen.entity.GenDataObj;
import com.lframework.starter.web.gen.entity.GenDataObjDetail;
import com.lframework.starter.web.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.web.gen.enums.GenCustomListDetailType;
import com.lframework.starter.web.gen.enums.GenDataType;
import com.lframework.starter.web.gen.enums.GenQueryType;
import com.lframework.starter.web.gen.enums.GenViewType;
import com.lframework.starter.web.gen.service.GenCustomListDetailService;
import com.lframework.starter.web.gen.service.GenCustomListHandleColumnService;
import com.lframework.starter.web.gen.service.GenCustomListQueryParamsService;
import com.lframework.starter.web.gen.service.GenCustomListService;
import com.lframework.starter.web.gen.service.GenCustomListToolbarService;
import com.lframework.starter.web.gen.service.GenCustomSelectorService;
import com.lframework.starter.web.gen.service.GenDataEntityDetailService;
import com.lframework.starter.web.gen.service.GenDataObjDetailService;
import com.lframework.starter.web.gen.service.GenDataObjQueryDetailService;
import com.lframework.starter.web.gen.service.GenDataObjService;
import com.lframework.starter.web.inner.entity.SysDataDic;
import com.lframework.starter.web.inner.service.system.SysDataDicService;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.EnumUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

public class CustomListBuilder {

  @Autowired
  private GenCustomListService genCustomListService;

  @Autowired
  private GenCustomListDetailService genCustomListDetailService;

  @Autowired
  private GenCustomListQueryParamsService genCustomListQueryParamsService;

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Autowired
  private GenDataObjService genDataObjService;

  @Autowired
  private GenDataObjDetailService genDataObjDetailService;

  @Autowired
  private GenDataObjQueryDetailService genDataObjQueryDetailService;

  @Autowired
  private DataObjectBuilder dataObjectBuilder;

  @Autowired
  private GenCustomListToolbarService genCustomListToolbarService;

  @Autowired
  private GenCustomListHandleColumnService genCustomListHandleColumnService;

  public DataObjectQueryObj buildQueryObj(String id, DataObjectQueryParamObj queryParamObj) {
    GenCustomList customList = genCustomListService.findById(id);
    if (customList == null) {
      throw new DefaultClientException("自定义列表不存在！");
    }

    CustomListBuilder customListBuilder = ApplicationUtil.getBean(CustomListBuilder.class);
    CustomListConfig config = customListBuilder.buildConfig(id);

    DataObjectQueryObj obj = dataObjectBuilder.buildQueryObj(customList.getDataObjId());
    obj.setQueryParamObj(this.buildQueryParamObj(queryParamObj, config));
    obj.setSuffixSql(customList.getSuffixSql());
    obj.setQueryPrefixSql(customList.getQueryPrefixSql());
    obj.setQuerySuffixSql(customList.getQuerySuffixSql());

    return obj;
  }

  @Cacheable(value = CustomListConfig.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  public CustomListConfig buildConfig(String id) {

    // 先查询配置信息
    GenCustomList data = genCustomListService.findById(id);

    if (data == null) {
      throw new DefaultClientException("自定义列表不存在！");
    }

    CustomListConfig result = new CustomListConfig();

    List<GenCustomListToolbar> toolbars = genCustomListToolbarService
        .getByCustomListId(data.getId());
    if (!CollectionUtil.isEmpty(toolbars)) {
      result.setToolbars(toolbars.stream().map(t -> {
        Toolbar toolbar = new Toolbar();
        toolbar.setId(t.getId());
        toolbar.setName(t.getName());
        toolbar.setViewType(t.getViewType().getCode());
        toolbar.setBtnType(t.getBtnType().getCode());
        toolbar.setBtnConfig(t.getBtnConfig());
        toolbar.setRequestParam(t.getRequestParam());
        toolbar.setIcon(t.getIcon());

        return toolbar;
      }).collect(Collectors.toList()));
    }

    List<GenCustomListHandleColumn> handleColumns = genCustomListHandleColumnService
        .getByCustomListId(data.getId());
    if (!CollectionUtil.isEmpty(handleColumns)) {
      result.setHandleColumns(handleColumns.stream().map(t -> {
        HandleColumn handleColumn = new HandleColumn();
        handleColumn.setId(t.getId());
        handleColumn.setName(t.getName());
        handleColumn.setViewType(t.getViewType().getCode());
        handleColumn.setBtnType(t.getBtnType().getCode());
        handleColumn.setBtnConfig(t.getBtnConfig());
        handleColumn.setRequestParam(t.getRequestParam());
        handleColumn.setIcon(t.getIcon());
        handleColumn.setWidth(t.getWidth());

        return handleColumn;
      }).collect(Collectors.toList()));
    }

    List<GenCustomListQueryParams> genCustomListQueryParamsList = genCustomListQueryParamsService
        .getByCustomListId(
            data.getId());
    List<GenCustomListDetail> genCustomListDetailList = genCustomListDetailService
        .getByCustomListId(
            data.getId());

    List<QueryParam> queryParams = new ArrayList<>();

    if (CollectionUtil.isNotEmpty(genCustomListQueryParamsList)) {
      for (GenCustomListQueryParams genCustomListQueryParams : genCustomListQueryParamsList) {
        GenDataEntityDetail entityDetail = genDataEntityDetailService.getById(
            genCustomListQueryParams.getDataEntityId());

        String relaId = genCustomListQueryParams.getRelaId();
        GenDataObj dataObj = null;
        GenDataObjDetail dataObjDetail = null;
        if (genCustomListQueryParams.getType() == GenCustomListDetailType.MAIN_TABLE) {
          dataObj = genDataObjService.findById(relaId);
        } else if (genCustomListQueryParams.getType() == GenCustomListDetailType.SUB_TALBE) {
          dataObjDetail = genDataObjDetailService.getById(relaId);
        } else {
          throw new DefaultSysException("不支持的类型！");
        }

        QueryParam queryParam = new QueryParam();
        queryParam.setTableAlias(
            genCustomListQueryParams.getType() == GenCustomListDetailType.SUB_TALBE
                ? dataObjDetail.getSubTableAlias() : dataObj.getMainTableAlias());
        queryParam.setColumnName(entityDetail.getDbColumnName());
        queryParam.setName(entityDetail.getName());
        queryParam.setFrontShow(genCustomListQueryParams.getFrontShow());
        queryParam.setQueryType(genCustomListQueryParams.getQueryType().getCode());
        queryParam.setFormWidth(genCustomListQueryParams.getFormWidth());
        queryParam.setViewType(entityDetail.getViewType().getCode());
        queryParam.setDataType(entityDetail.getDataType().getCode());
        queryParam.setFixEnum(entityDetail.getFixEnum());
        queryParam.setFrontType(entityDetail.getEnumFront());
        if (StringUtil.isNotEmpty(genCustomListQueryParams.getDefaultValue())) {
          queryParam.setDefaultValue(Convert.convert(
              entityDetail.getViewType() == GenViewType.DATE_RANGE ? String.class
                  : entityDetail.getDataType().getClazz(),
              genCustomListQueryParams.getDefaultValue()));
        }
        queryParam.setHasAvailableTag(
            entityDetail.getViewType() == GenViewType.SELECT
                && entityDetail.getDataType() == GenDataType.BOOLEAN
                && "available".equals(entityDetail.getColumnName()));

        if (entityDetail.getViewType() == GenViewType.DATA_DIC) {
          SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
          SysDataDic dic = sysDataDicService.findById(entityDetail.getDataDicId());
          queryParam.setDataDicCode(dic.getCode());
        } else if (entityDetail.getViewType() == GenViewType.CUSTOM_SELECTOR) {
          GenCustomSelectorService genCustomSelectorService = ApplicationUtil
              .getBean(GenCustomSelectorService.class);
          GenCustomSelector selector = genCustomSelectorService
              .findById(entityDetail.getCustomSelectorId());
          queryParam.setCustomSelectorId(selector.getId());
        }

        queryParams.add(queryParam);
      }
    }

    result.setQueryParams(queryParams);

    ListConfig listConfig = new ListConfig();
    listConfig.setId(data.getId());
    listConfig.setListType(data.getListType().getCode());
    listConfig.setLabelWidth(data.getLabelWidth());
    listConfig.setHasPage(data.getHasPage());
    listConfig.setTreeData(data.getTreeData());
    GenDataEntityDetail idColumnEntityDetail = genDataEntityDetailService
        .getById(data.getIdColumn());
    GenDataObj idColumnDataObj = genDataObjService.findById(data.getIdColumnRelaId());
    GenDataObjDetail idColumnDataObjDetail = genDataObjDetailService
        .getById(data.getIdColumnRelaId());

    listConfig.setIdColumn(
        (idColumnDataObj == null ? idColumnDataObjDetail.getSubTableAlias()
            : idColumnDataObj.getMainTableAlias()) + "_"
            + idColumnEntityDetail.getDbColumnName());
    listConfig.setAllowExport(data.getAllowExport());

    if (data.getTreeData()) {
      GenDataEntityDetail entityDetail = genDataEntityDetailService
          .getById(data.getTreePidColumn());
      GenDataObj dataObj = genDataObjService.findById(data.getTreePidColumnRelaId());
      GenDataObjDetail dataObjDetail = genDataObjDetailService
          .getById(data.getTreePidColumnRelaId());

      listConfig.setTreePidColumn(
          (dataObj == null ? dataObjDetail.getSubTableAlias() : dataObj.getMainTableAlias()) + "_"
              + entityDetail.getDbColumnName());

      entityDetail = genDataEntityDetailService.getById(data.getTreeNodeColumn());
      dataObj = genDataObjService.findById(data.getTreeNodeColumnRelaId());
      dataObjDetail = genDataObjDetailService
          .getById(data.getTreeNodeColumnRelaId());

      listConfig.setTreeNodeColumn(
          (dataObj == null ? dataObjDetail.getSubTableAlias() : dataObj.getMainTableAlias()) + "_"
              + entityDetail.getDbColumnName());

      listConfig.setTreeChildrenKey(data.getTreeChildrenKey());
    }

    List<FieldConfig> fieldConfigs = new ArrayList<>();
    if (CollectionUtil.isNotEmpty(genCustomListDetailList)) {
      for (GenCustomListDetail genCustomListDetail : genCustomListDetailList) {
        String relaId = genCustomListDetail.getRelaId();
        GenDataEntityDetail entityDetail = null;
        GenDataObjQueryDetail dataObjQueryDetail = null;

        GenDataObj dataObj = null;
        GenDataObjDetail dataObjDetail = null;
        if (genCustomListDetail.getType() == GenCustomListDetailType.MAIN_TABLE) {
          entityDetail = genDataEntityDetailService.getById(genCustomListDetail.getDataEntityId());
          dataObj = genDataObjService.findById(relaId);
        } else if (genCustomListDetail.getType() == GenCustomListDetailType.SUB_TALBE) {
          entityDetail = genDataEntityDetailService.getById(genCustomListDetail.getDataEntityId());
          dataObjDetail = genDataObjDetailService.getById(relaId);
        } else {
          dataObjQueryDetail = genDataObjQueryDetailService.getById(
              genCustomListDetail.getRelaId());
        }
        FieldConfig fieldConfig = new FieldConfig();
        fieldConfig.setName((genCustomListDetail.getType() == GenCustomListDetailType.MAIN_TABLE
            || genCustomListDetail.getType() == GenCustomListDetailType.SUB_TALBE)
            ? entityDetail.getName() : dataObjQueryDetail.getCustomName());

        if (genCustomListDetail.getType() == GenCustomListDetailType.MAIN_TABLE) {
          fieldConfig.setColumnName(
              dataObj.getMainTableAlias() + "_" + entityDetail.getDbColumnName());
        } else if (genCustomListDetail.getType() == GenCustomListDetailType.SUB_TALBE) {
          fieldConfig.setColumnName(
              dataObjDetail.getSubTableAlias() + "_" + entityDetail.getDbColumnName());
        } else {
          fieldConfig.setColumnName("custom_" + dataObjQueryDetail.getCustomAlias());
        }

        fieldConfig.setWidthType(genCustomListDetail.getWidthType().getCode());
        fieldConfig.setWidth(genCustomListDetail.getWidth());
        fieldConfig.setSortable(genCustomListDetail.getSortable());
        fieldConfig.setFormatter(genCustomListDetail.getFormatter());
        if (genCustomListDetail.getType() == GenCustomListDetailType.MAIN_TABLE
            || genCustomListDetail.getType() == GenCustomListDetailType.SUB_TALBE) {
          fieldConfig.setIsNumberType(GenDataType.isNumberType(entityDetail.getDataType()));
          fieldConfig.setHasAvailableTag(
              entityDetail.getViewType() == GenViewType.SELECT
                  && entityDetail.getDataType() == GenDataType.BOOLEAN
                  && "available".equals(entityDetail.getColumnName()));
          fieldConfig.setFrontType(entityDetail.getEnumFront());
          fieldConfig.setFixEnum(entityDetail.getFixEnum());
          fieldConfig.setDataType(entityDetail.getDataType().getCode());
        } else {
          fieldConfig.setIsNumberType(GenDataType.isNumberType(dataObjQueryDetail.getDataType()));
          fieldConfig.setHasAvailableTag(false);
          fieldConfig.setFrontType(StringPool.EMPTY_STR);
          fieldConfig.setFixEnum(false);
          fieldConfig.setDataType(dataObjQueryDetail.getDataType().getCode());
        }

        fieldConfigs.add(fieldConfig);
      }
    }

    listConfig.setFields(fieldConfigs);
    result.setListConfig(listConfig);

    return result;
  }

  private DataObjectQueryParamObj buildQueryParamObj(DataObjectQueryParamObj queryParamObj,
      CustomListConfig config) {
    if (queryParamObj == null) {
      return null;
    }

    if (CollectionUtil.isNotEmpty(queryParamObj.getConditions())) {
      for (DataObjectQueryParamObj.Condition condition : queryParamObj.getConditions()) {
        FieldConfig fieldConfig = config.getListConfig().getFields().stream().filter(
            t -> (condition.getTableAlias() + "_" + condition.getColumnName())
                .equals(t.getColumnName())).findFirst().orElse(null);
        if (fieldConfig != null) {
          if (condition.getValue() != null) {
            condition.setValue(Convert.convert(
                EnumUtil.getByCode(GenDataType.class, fieldConfig.getDataType()).getClazz(),
                condition.getValue()));
          } else if (CollectionUtil.isNotEmpty(condition.getValues())) {
            condition.setValues(condition.getValues().stream().map(t -> Convert.convert(
                EnumUtil.getByCode(GenDataType.class, fieldConfig.getDataType()).getClazz(), t))
                .collect(Collectors.toList()));
          }
        }
        GenQueryType queryType = EnumUtil.getByCode(GenQueryType.class, condition.getQueryType());
        if (queryType == GenQueryType.IN) {
          condition.setValuePrefix("(");
          condition.setValueSuffix(")");
        } else if (queryType == GenQueryType.NOT_IN) {
          condition.setValuePrefix("(");
          condition.setValueSuffix(")");
        } else if (queryType == GenQueryType.LEFT_LIKE) {
          condition.setValuePrefix("CONCAT('%',");
          condition.setValueSuffix(")");
        } else if (queryType == GenQueryType.RIGHT_LIKE) {
          condition.setValuePrefix("CONCAT(");
          condition.setValueSuffix(", '%')");
        } else if (queryType == GenQueryType.AROUND_LIKE) {
          condition.setValuePrefix("CONCAT('%',");
          condition.setValueSuffix(", '%')");
        } else {
          condition.setValuePrefix(StringPool.EMPTY_STR);
          condition.setValueSuffix(StringPool.EMPTY_STR);
        }
      }
    }
    return queryParamObj;
  }
}
