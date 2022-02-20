package com.lframework.gen.bo.dataobj;

import com.lframework.gen.dto.dataobj.GenQueryColumnConfigDto;
import com.lframework.gen.enums.GenQueryWidthType;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenQueryColumnConfigBo extends BaseBo<GenQueryColumnConfigDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 宽度类型
     */
    private Integer widthType;

    /**
     * 宽度
     */
    private Integer width;

    /**
     * 是否页面排序
     */
    private Boolean sortable;

    /**
     * 排序编号
     */
    private Integer orderNo;

    public GenQueryColumnConfigBo() {

    }

    public GenQueryColumnConfigBo(GenQueryColumnConfigDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<GenQueryColumnConfigDto> convert(GenQueryColumnConfigDto dto) {

        return super.convert(dto, GenQueryColumnConfigBo::getWidthType);
    }

    @Override
    protected void afterInit(GenQueryColumnConfigDto dto) {

        this.widthType = dto.getWidthType().getCode();
    }
}
