package com.lframework.starter.web.inner.bo.system.parameter;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.inner.entity.SysParameter;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统参数 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysParameterBo extends BaseBo<SysParameter> {

  /**
   * ID
   */
  @JsonSerialize(using = ToStringSerializer.class)
  @Schema(description = "ID")
  private Long id;

  /**
   * 键
   */
  @Schema(description = "键")
  private String pmKey;

  /**
   * 值
   */
  @Schema(description = "值")
  private String pmValue;

  /**
   * 是否加密值
   */
  @Schema(description = "是否加密值")
  private Boolean isEncrypt;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetSysParameterBo() {

  }

  public GetSysParameterBo(SysParameter dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysParameter dto) {

    if (Boolean.TRUE.equals(dto.getIsEncrypt())) {
      this.pmValue = StringPool.ENCRYPT_MASK;
    }
  }
}
