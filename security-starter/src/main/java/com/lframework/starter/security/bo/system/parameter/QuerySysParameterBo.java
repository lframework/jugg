package com.lframework.starter.security.bo.system.parameter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.entity.SysParameter;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统参数 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysParameterBo extends BaseBo<SysParameter> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @ApiModelProperty("ID")
  private Long id;

  /**
   * 键
   */
  @ApiModelProperty("键")
  private String pmKey;

  /**
   * 值
   */
  @ApiModelProperty("值")
  private String pmValue;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  public QuerySysParameterBo() {

  }

  public QuerySysParameterBo(SysParameter dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysParameter dto) {
    IUserService userService = ApplicationUtil.getBean(IUserService.class);
    UserDto createBy = userService.findById(dto.getCreateBy());

    this.createBy = createBy.getName();
  }
}
