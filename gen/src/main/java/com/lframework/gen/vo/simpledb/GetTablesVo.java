package com.lframework.gen.vo.simpledb;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class GetTablesVo implements BaseVo, Serializable {

  /**
   * 是否当前数据库
   */
  @ApiModelProperty("是否当前数据库")
  private Boolean isCurrentDb;

}
