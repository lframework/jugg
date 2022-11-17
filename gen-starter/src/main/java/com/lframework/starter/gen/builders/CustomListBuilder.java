package com.lframework.starter.gen.builders;

import cn.hutool.core.convert.Convert;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.components.custom.list.CustomListConfig;
import com.lframework.starter.gen.components.custom.list.CustomListConfig.FieldConfig;
import com.lframework.starter.gen.components.custom.list.CustomListConfig.ListConfig;
import com.lframework.starter.gen.components.custom.list.CustomListConfig.QueryParam;
import com.lframework.starter.gen.components.data.obj.DataObjectQueryObj;
import com.lframework.starter.gen.components.data.obj.DataObjectQueryParamObj;
import com.lframework.starter.gen.entity.GenCustomList;
import com.lframework.starter.gen.entity.GenCustomListDetail;
import com.lframework.starter.gen.entity.GenCustomListQueryParams;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.entity.GenDataObjDetail;
import com.lframework.starter.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.gen.enums.GenCustomListDetailType;
import com.lframework.starter.gen.enums.GenDataType;
import com.lframework.starter.gen.enums.GenQueryType;
import com.lframework.starter.gen.enums.GenViewType;
import com.lframework.starter.gen.service.IGenCustomListDetailService;
import com.lframework.starter.gen.service.IGenCustomListQueryParamsService;
import com.lframework.starter.gen.service.IGenCustomListService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDataObjDetailService;
import com.lframework.starter.gen.service.IGenDataObjQueryDetailService;
import com.lframework.starter.gen.service.IGenDataObjService;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.service.system.ISysDataDicService;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CustomListBuilder {

  @Autowired
  private IGenCustomListService genCustomListService;

  @Autowired
  private IGenCustomListDetailService genCustomListDetailService;

  @Autowired
  private IGenCustomListQueryParamsService genCustomListQueryParamsService;

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Autowired
  private IGenDataObjService genDataObjService;

  @Autowired
  private IGenDataObjDetailService genDataObjDetailService;

  @Autowired
  private IGenDataObjQueryDetailService genDataObjQueryDetailService;

  @Autowired
  private DataObjectBuilder dataObjectBuilder;

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

  @Cacheable(value = CustomListConfig.CACHE_NAME, key = "#id", unless = "#result == null")
  public CustomListConfig buildConfig(String id) {

    // 先查询配置信息
    GenCustomList data = genCustomListService.findById(id);

    if (data == null) {
      throw new DefaultClientException("自定义列表不存在！");
    }

    List<GenCustomListQueryParams> genCustomListQueryParamsList = genCustomListQueryParamsService
        .getByCustomListId(
            data.getId());
    List<GenCustomListDetail> genCustomListDetailList = genCustomListDetailService
        .getByCustomListId(
            data.getId());

    CustomListConfig result = new CustomListConfig();

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
        queryParam.setQueryType(genCustomListQueryParams.getQueryType().getCode());
        queryParam.setFormWidth(genCustomListQueryParams.getFormWidth());
        queryParam.setViewType(entityDetail.getViewType().getCode());
        queryParam.setDataType(entityDetail.getDataType().getCode());
        queryParam.setFixEnum(entityDetail.getFixEnum());
        queryParam.setFrontType(entityDetail.getEnumFront());
        queryParam.setDefaultValue(Convert.convert(
            entityDetail.getViewType() == GenViewType.DATE_RANGE ? Integer.class
                : entityDetail.getDataType().getClazz(),
            genCustomListQueryParams.getDefaultValue()));
        queryParam.setHasAvailableTag(
            entityDetail.getViewType() == GenViewType.SELECT
                && entityDetail.getDataType() == GenDataType.BOOLEAN
                && "available".equals(entityDetail.getColumnName()));

        if (entityDetail.getViewType() == GenViewType.DATA_DIC) {
          ISysDataDicService sysDataDicService = ApplicationUtil.getBean(ISysDataDicService.class);
          SysDataDic dic = sysDataDicService.findById(entityDetail.getDataDicId());
          queryParam.setDataDicCode(dic.getCode());
        }

        queryParams.add(queryParam);
      }
    }

    result.setQueryParams(queryParams);

    ListConfig listConfig = new ListConfig();
    listConfig.setLabelWidth(data.getLabelWidth());
    listConfig.setHasPage(data.getHasPage());
    listConfig.setTreeData(data.getTreeData());
    if (data.getTreeData()) {
        GenDataEntityDetail entityDetail = genDataEntityDetailService.getById(data.getTreeIdColumn());
        GenDataObj dataObj = genDataObjService.findById(data.getTreeIdColumnRelaId());
        GenDataObjDetail dataObjDetail = genDataObjDetailService
            .getById(data.getTreeIdColumnRelaId());

        listConfig.setTreeIdColumn(
            (dataObj == null ? dataObjDetail.getSubTableAlias() : dataObj.getMainTableAlias()) + "_"
                + entityDetail.getDbColumnName());

        entityDetail = genDataEntityDetailService.getById(data.getTreePidColumn());
        dataObj = genDataObjService.findById(data.getTreePidColumnRelaId());
        dataObjDetail = genDataObjDetailService
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
