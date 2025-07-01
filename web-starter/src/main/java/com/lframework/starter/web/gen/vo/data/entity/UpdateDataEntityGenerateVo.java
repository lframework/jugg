package com.lframework.starter.web.gen.vo.data.entity;

import com.lframework.starter.web.gen.vo.UpdateCreateColumnConfigVo;
import com.lframework.starter.web.gen.vo.UpdateDetailColumnConfigVo;
import com.lframework.starter.web.gen.vo.UpdateGenerateInfoVo;
import com.lframework.starter.web.gen.vo.UpdateQueryColumnConfigVo;
import com.lframework.starter.web.gen.vo.UpdateQueryParamsColumnConfigVo;
import com.lframework.starter.web.gen.vo.UpdateUpdateColumnConfigVo;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDataEntityGenerateVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 基础设置
   */
  @ApiModelProperty(value = "基础设置", required = true)
  @Valid
  @NotNull(message = "基本设置不能为空！")
  private UpdateGenerateInfoVo generateInfo;

  /**
   * 新增配置
   */
  @ApiModelProperty("新增配置")
  @Valid
  private List<UpdateCreateColumnConfigVo> createConfigs;

  /**
   * 修改配置
   */
  @ApiModelProperty("修改配置")
  @Valid
  private List<UpdateUpdateColumnConfigVo> updateConfigs;

  /**
   * 查询配置
   */
  @ApiModelProperty("查询配置")
  @Valid
  private List<UpdateQueryColumnConfigVo> queryConfigs;

  /**
   * 查询参数配置
   */
  @ApiModelProperty("查询参数配置")
  @Valid
  private List<UpdateQueryParamsColumnConfigVo> queryParamsConfigs;

  /**
   * 详情配置
   */
  @ApiModelProperty("详情配置")
  @Valid
  private List<UpdateDetailColumnConfigVo> detailConfigs;
}
