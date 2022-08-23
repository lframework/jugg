package com.lframework.starter.gen.bo.dataobj;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.gen.dto.dataobj.DataObjectDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QueryDataObjectBo extends BaseBo<DataObjectDto> {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 类型 1 数据库单表
   */
  @ApiModelProperty("类型 1 数据库单表")
  private Integer type;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Integer genStatus;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人ID
   */
  @ApiModelProperty("创建人ID")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人ID
   */
  @ApiModelProperty("修改人ID")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime updateTime;

  public QueryDataObjectBo() {

  }

  public QueryDataObjectBo(DataObjectDto dto) {

    super(dto);
  }

  @Override
  public <A> BaseBo<DataObjectDto> convert(DataObjectDto dto) {

    return super.convert(dto, QueryDataObjectBo::getType, QueryDataObjectBo::getGenStatus);
  }

  @Override
  protected void afterInit(DataObjectDto dto) {

    this.type = dto.getType().getCode();
    this.genStatus = dto.getGenStatus().getCode();
  }
}
