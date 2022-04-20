package com.lframework.starter.security.bo.message;

import com.lframework.starter.mybatis.dto.message.TodoTaskDto;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TodoTaskBo extends BaseBo<TodoTaskDto> {

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

  public TodoTaskBo(TodoTaskDto dto) {

    super(dto);
  }
}
