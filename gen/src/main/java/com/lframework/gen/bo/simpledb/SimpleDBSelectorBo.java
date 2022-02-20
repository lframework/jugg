package com.lframework.gen.bo.simpledb;

import com.lframework.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SimpleDBSelectorBo extends BaseBo<SimpleDBDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 库名
     */
    private String tableSchema;

    /**
     * 表名
     */
    private String tableName;

    public SimpleDBSelectorBo() {

    }

    public SimpleDBSelectorBo(SimpleDBDto dto) {

        super(dto);
    }
}
