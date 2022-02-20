package com.lframework.gen.bo.dataobj;

import com.lframework.gen.dto.dataobj.GenDetailColumnConfigDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenDetailColumnConfigBo extends BaseBo<GenDetailColumnConfigDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 列宽
     */
    private Integer span;

    /**
     * 排序编号
     */
    private Integer orderNo;

    public GenDetailColumnConfigBo() {

    }

    public GenDetailColumnConfigBo(GenDetailColumnConfigDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<GenDetailColumnConfigDto> convert(GenDetailColumnConfigDto dto) {

        return super.convert(dto);
    }

    @Override
    protected void afterInit(GenDetailColumnConfigDto dto) {

        super.afterInit(dto);
    }
}
