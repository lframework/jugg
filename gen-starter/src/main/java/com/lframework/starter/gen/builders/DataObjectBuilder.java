package com.lframework.starter.gen.builders;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.components.data.obj.DataObjectQueryObj;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.entity.GenDataObjDetail;
import com.lframework.starter.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDataEntityService;
import com.lframework.starter.gen.service.IGenDataObjDetailService;
import com.lframework.starter.gen.service.IGenDataObjQueryDetailService;
import com.lframework.starter.gen.service.IGenDataObjService;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * 数据对象Builder
 */
@Component
public class DataObjectBuilder {

    @Autowired
    private IGenDataObjService genDataObjService;

    @Autowired
    private IGenDataObjDetailService genDataObjDetailService;

    @Autowired
    private IGenDataObjQueryDetailService genDataObjQueryDetailService;

    @Autowired
    private IGenDataEntityService genDataEntityService;

    @Autowired
    private IGenDataEntityDetailService genDataEntityDetailService;

    @Cacheable(value = DataObjectQueryObj.CACHE_NAME, key = "#id", unless = "#result == null")
    public DataObjectQueryObj buildQueryObj(String id) {
        // 先查询配置信息
        GenDataObj data = genDataObjService.findById(id);

        if (data == null) {
            throw new DefaultClientException("数据对象不存在！");
        }

        List<GenDataObjDetail> details = genDataObjDetailService.getByObjId(data.getId());
        List<GenDataObjQueryDetail> queryDetails = genDataObjQueryDetailService.getByObjId(data.getId());

        String mainTableId = data.getMainTableId();
        GenDataEntity dataEntity = genDataEntityService.findById(mainTableId);
        if (dataEntity == null) {
            throw new DefaultClientException("数据实体不存在！");
        }

        List<GenDataEntityDetail> entityDetails = genDataEntityDetailService.getByEntityId(dataEntity.getId());

        DataObjectQueryObj queryObj = new DataObjectQueryObj();
        List<DataObjectQueryObj.QueryField> fields = new ArrayList<>();

        // 主表字段
        if (CollectionUtil.isNotEmpty(entityDetails)) {
            for (GenDataEntityDetail entityDetail : entityDetails) {
                DataObjectQueryObj.QueryField field = new DataObjectQueryObj.QueryField();
                field.setTableAlias(data.getMainTableAlias());
                field.setColumnName(entityDetail.getDbColumnName());
                field.setColumnAlias(data.getMainTableAlias() + "_" + entityDetail.getDbColumnName());
                field.setDataType(entityDetail.getDataType());

                fields.add(field);
            }
        }

        // 子表字段
        if (CollectionUtil.isNotEmpty(details)) {
            for (GenDataObjDetail detail : details) {
                GenDataEntity subTable = genDataEntityService.findById(detail.getSubTableId());
                List<GenDataEntityDetail> subTableDetails = genDataEntityDetailService.getByEntityId(subTable.getId());
                for (GenDataEntityDetail subTableDetail : subTableDetails) {
                    DataObjectQueryObj.QueryField field = new DataObjectQueryObj.QueryField();
                    field.setTableAlias(detail.getSubTableAlias());
                    field.setColumnName(subTableDetail.getDbColumnName());
                    field.setColumnAlias(detail.getSubTableAlias() + "_" + subTableDetail.getDbColumnName());
                    field.setDataType(subTableDetail.getDataType());

                    fields.add(field);
                }
            }
        }

        // 自定义查询字段
        if (CollectionUtil.isNotEmpty(queryDetails)) {
            for (GenDataObjQueryDetail queryDetail : queryDetails) {
                DataObjectQueryObj.QueryField field = new DataObjectQueryObj.QueryField();
                field.setColumnName(queryDetail.getCustomSql());
                field.setColumnAlias("custom_" + queryDetail.getCustomAlias());
                field.setDataType(queryDetail.getDataType());

                fields.add(field);
            }
        }


        queryObj.setFields(fields);
        queryObj.setMainTable(dataEntity.getTableName());
        queryObj.setMainTableAlias(data.getMainTableAlias());

        // 关联字表
        List<DataObjectQueryObj.QuerySubTable> subTables = new ArrayList<>();
        for (GenDataObjDetail detail : details) {
            GenDataEntity subTable= genDataEntityService.getById(detail.getSubTableId());

            DataObjectQueryObj.QuerySubTable querySubTable = new DataObjectQueryObj.QuerySubTable();
            querySubTable.setSubTable(subTable.getTableName());
            querySubTable.setSubTableAlias(detail.getSubTableAlias());
            querySubTable.setJoinType(detail.getRelaMode().getSql());

            String[] mainTableDetailIds = detail.getMainTableDetailIds().split(StringPool.STR_SPLIT);
            String[] subTableDetailIds = detail.getSubTableDetailIds().split(StringPool.STR_SPLIT);

            querySubTable.setJoinCondition(new ArrayList<>());

            for (int i = 0; i < mainTableDetailIds.length; i++) {
                String mainTableDetailId = mainTableDetailIds[i];
                String subTableDetailId = subTableDetailIds[i];

                GenDataEntityDetail mainTableDetail = genDataEntityDetailService.getById(mainTableDetailId);
                GenDataEntityDetail subTableDetail = genDataEntityDetailService.getById(subTableDetailId);

                querySubTable.getJoinCondition().add(new AbstractMap.SimpleEntry<>(mainTableDetail.getDbColumnName(), subTableDetail.getDbColumnName()));
            }



            subTables.add(querySubTable);
        }
        queryObj.setSubTables(subTables);

        return queryObj;
    }
}
