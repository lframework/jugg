package com.lframework.starter.gen.bo.data.obj;

import com.lframework.starter.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GenDataObjQueryDetailBo extends BaseBo<GenDataObjQueryDetail> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 显示名称
   */
  @ApiModelProperty("显示名称")
  private String customName;

  /**
   * 自定义SQL
   */
  @ApiModelProperty("自定义SQL")
  private String customSql;

  /**
   * 别名
   */
  @ApiModelProperty("别名")
  private String customAlias;

  public GenDataObjQueryDetailBo() {

  }

  public GenDataObjQueryDetailBo(GenDataObjQueryDetail dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<GenDataObjQueryDetail> convert(GenDataObjQueryDetail dto) {

    return super.convert(dto);
  }
}
