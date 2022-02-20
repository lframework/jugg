package com.lframework.gen.bo.dataobj;

import com.lframework.gen.dto.dataobj.GenQueryParamsColumnConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenQueryParamsColumnConfigBo extends BaseBo<GenQueryParamsColumnConfigDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 查询类型
     */
    private Integer queryType;

    /**
     * 排序编号
     */
    private Integer orderNo;

    public GenQueryParamsColumnConfigBo() {

    }

    public GenQueryParamsColumnConfigBo(GenQueryParamsColumnConfigDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<GenQueryParamsColumnConfigDto> convert(GenQueryParamsColumnConfigDto dto) {

        return super.convert(dto, GenQueryParamsColumnConfigBo::getQueryType);
    }

    @Override
    protected void afterInit(GenQueryParamsColumnConfigDto dto) {

        this.queryType = dto.getQueryType().getCode();
    }
}
