package com.lframework.gen.bo.simpledb;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.gen.dto.simpledb.SimpleTableDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class GetSimpleTableBo extends BaseBo<SimpleTableDto> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 数据表所属的数据库名
     */
    private String tableSchema;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 字段信息
     */
    private List<GetSimpleTableColumnBo> columns;

    /**
     * 数据库引擎
     */
    private String engine;

    /**
     * 字符校验编码集
     */
    private String tableCollation;

    /**
     * 备注
     */
    private String tableComment;

    public GetSimpleTableBo() {

    }

    public GetSimpleTableBo(SimpleTableDto dto) {

        super(dto);
    }

    @Override
    public <A> BaseBo<SimpleTableDto> convert(SimpleTableDto dto) {

        return super.convert(dto, GetSimpleTableBo::getColumns);
    }

    @Override
    protected void afterInit(SimpleTableDto dto) {

        if (!ObjectUtil.isNull(dto) && !CollectionUtil.isEmpty(dto.getColumns())) {
            List<GetSimpleTableColumnBo> columns = dto.getColumns().stream().map(GetSimpleTableColumnBo::new)
                    .collect(Collectors.toList());
            this.columns = columns;
        }
    }
}
