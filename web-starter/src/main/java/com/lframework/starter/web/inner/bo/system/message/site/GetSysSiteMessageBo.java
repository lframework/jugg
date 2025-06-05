package com.lframework.starter.web.inner.bo.system.message.site;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 站内信 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysSiteMessageBo extends BaseBo<SysSiteMessage> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 内容
   */
  @ApiModelProperty("内容")
  private String content;

  public GetSysSiteMessageBo() {

  }

  public GetSysSiteMessageBo(SysSiteMessage dto) {

    super(dto);
  }

}
