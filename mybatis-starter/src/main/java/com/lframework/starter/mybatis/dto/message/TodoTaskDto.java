package com.lframework.starter.mybatis.dto.message;

import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class TodoTaskDto extends BaseBo implements MessageBusDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  private String createTime;

  /**
   * 跳转Url
   */
  @ApiModelProperty("跳转Url")
  private String jumpUrl;

  @Override
  public Boolean getReaded() {
    return false;
  }
}
