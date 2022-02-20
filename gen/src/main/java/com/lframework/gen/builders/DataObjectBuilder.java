package com.lframework.gen.builders;

import com.lframework.gen.components.DataObject;
import com.lframework.gen.components.DataObjectColumn;
import com.lframework.gen.dto.dataobj.DataObjectDto;
import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.gen.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据对象Builder
 */
@Component
public class DataObjectBuilder {

    @Autowired
    private IDataObjectService dataObjectService;

    @Autowired
    private IGenerateInfoService generateInfoService;

    @Autowired
    private IDataObjectColumnService dataObjectColumnService;

    @Autowired
    private IGenCreateColumnConfigService genCreateColumnConfigService;

    @Autowired
    private IGenUpdateColumnConfigService genUpdateColumnConfigService;

    @Autowired
    private IGenQueryColumnConfigService genQueryColumnConfigService;

    @Autowired
    private IGenQueryParamsColumnConfigService genQueryParamsColumnConfigService;

    @Autowired
    private IGenDetailColumnConfigService genDetailColumnConfigService;

    /**
     * 根据数据对象ID构建
     * @param id
     * @return
     */
    public DataObject build(String id) {

        // 根据ID查询数据对象
        DataObjectDto dataObject = dataObjectService.getById(id);
        DataObject result = new DataObject();
        result.setId(dataObject.getId());
        result.setCode(dataObject.getCode());
        result.setName(dataObject.getName());
        result.setType(dataObject.getType());
        result.setDescription(dataObject.getDescription());

        TableBuilder tableBuilder = TableBuilderFactory.getBuilder(dataObject.getType());

        result.setTable(tableBuilder.buildTable(dataObject.getId()));
        result.setColumns(this.buildColumns(dataObject.getId(), tableBuilder));
        result.setGenerateInfo(this.buildGenerateInfo(dataObject.getId()));

        return result;
    }

    private GenGenerateInfoDto buildGenerateInfo(String dataObjId) {

        return generateInfoService.getByDataObjId(dataObjId);
    }

    private List<DataObjectColumn> buildColumns(String dataObjId, TableBuilder tableBuilder) {

        List<DataObjectColumn> results = new ArrayList<>();
        List<GenDataObjectColumnDto> columns = dataObjectColumnService.getByDataObjId(dataObjId);
        for (GenDataObjectColumnDto column : columns) {
            DataObjectColumn result = new DataObjectColumn();
            result.setId(column.getId());
            result.setName(column.getName());
            result.setColumnName(column.getColumnName());
            result.setIsKey(column.getIsKey());
            result.setDataType(column.getDataType());
            result.setColumnOrder(column.getColumnOrder());
            result.setDescription(column.getDescription());
            result.setViewType(column.getViewType());
            result.setTableColumn(tableBuilder.buildTableColumn(column.getId()));
            result.setFixEnum(column.getFixEnum());
            result.setEnumBack(column.getEnumBack());
            result.setEnumFront(column.getEnumFront());
            result.setRegularExpression(column.getRegularExpression());
            result.setIsOrder(column.getIsOrder());
            if (result.getIsOrder()) {
                result.setOrderType(column.getOrderType());
            }

            result.setCreateConfig(genCreateColumnConfigService.getById(column.getId()));
            result.setUpdateConfig(genUpdateColumnConfigService.getById(column.getId()));
            result.setQueryConfig(genQueryColumnConfigService.getById(column.getId()));
            result.setQueryParamsConfig(genQueryParamsColumnConfigService.getById(column.getId()));
            result.setDetailConfig(genDetailColumnConfigService.getById(column.getId()));

            results.add(result);
        }

        return results;
    }
}
