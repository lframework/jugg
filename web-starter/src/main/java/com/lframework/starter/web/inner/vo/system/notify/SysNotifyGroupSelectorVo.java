package com.lframework.starter.web.inner.vo.system.notify;

import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class SysNotifyGroupSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;
}
