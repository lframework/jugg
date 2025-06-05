package com.lframework.starter.web.gen.bo.simpledb;

import com.lframework.starter.web.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimpleDBSelectorBo extends BaseBo<SimpleDBDto> {

  private static final long serialVersionUID = 1L;

  /**
   * 库名
   */
  @ApiModelProperty("库名")
  private String tableSchema;

  /**
   * 表名
   */
  @ApiModelProperty("表名")
  private String tableName;

  public SimpleDBSelectorBo() {

  }

  public SimpleDBSelectorBo(SimpleDBDto dto) {

    super(dto);
  }
}
