package com.lframework.gen.bo.dataobj;

import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.dto.dataobj.DataObjectGenerateDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class DataObjectGenerateBo extends BaseBo<DataObjectGenerateDto> {

    /**
     * 字段信息
     */
    private List<GenDataObjectColumnBo> columns;

    /**
     * 基本设置
     */
    private GenGenerateInfoBo generateInfo;

    /**
     * 新增配置
     */
    private List<GenCreateColumnConfigBo> createConfigs;

    /**
     * 修改配置
     */
    private List<GenUpdateColumnConfigBo> updateConfigs;

    /**
     * 查询配置
     */
    private List<GenQueryColumnConfigBo> queryConfigs;

    /**
     * 查询参数配置
     */
    private List<GenQueryParamsColumnConfigBo> queryParamsConfigs;

    /**
     * 详情配置
     */
    private List<GenDetailColumnConfigBo> detailConfigs;

    public DataObjectGenerateBo(DataObjectGenerateDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<DataObjectGenerateDto> convert(DataObjectGenerateDto dto) {

        return this;
    }

    @Override
    public <A> BaseBo<DataObjectGenerateDto> convert(DataObjectGenerateDto dto, SFunction<A, ?>... columns) {

        return this;
    }

    @Override
    protected void afterInit(DataObjectGenerateDto dto) {

        this.columns = dto.getColumns().stream().map(GenDataObjectColumnBo::new).collect(Collectors.toList());
        this.generateInfo = dto.getGenerateInfo() == null ? null : new GenGenerateInfoBo(dto.getGenerateInfo());
        if (!CollectionUtil.isEmpty(dto.getCreateConfigs())) {
            this.createConfigs = dto.getCreateConfigs().stream().map(GenCreateColumnConfigBo::new)
                    .collect(Collectors.toList());
        }

        if (!CollectionUtil.isEmpty(dto.getUpdateConfigs())) {
            this.updateConfigs = dto.getUpdateConfigs().stream().map(GenUpdateColumnConfigBo::new)
                    .collect(Collectors.toList());
        }

        if (!CollectionUtil.isEmpty(dto.getQueryConfigs())) {
            this.queryConfigs = dto.getQueryConfigs().stream().map(GenQueryColumnConfigBo::new)
                    .collect(Collectors.toList());
        }

        if (!CollectionUtil.isEmpty(dto.getQueryParamsConfigs())) {
            this.queryParamsConfigs = dto.getQueryParamsConfigs().stream().map(GenQueryParamsColumnConfigBo::new)
                    .collect(Collectors.toList());
        }

        if (!CollectionUtil.isEmpty(dto.getDetailConfigs())) {
            this.detailConfigs = dto.getDetailConfigs().stream().map(GenDetailColumnConfigBo::new)
                    .collect(Collectors.toList());
        }
    }
}
