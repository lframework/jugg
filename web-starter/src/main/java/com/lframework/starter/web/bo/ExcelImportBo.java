package com.lframework.starter.web.bo;

import com.lframework.starter.web.dto.VoidDto;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ExcelImportBo extends BaseBo<VoidDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 当前进度（条数）
   */
  @ApiModelProperty("当前进度（条数）")
  private Integer process = 0;

  /**
   * 成功进度（条数）
   */
  @ApiModelProperty("成功进度（条数）")
  private Integer successProcess = 0;

  /**
   * 提示信息
   */
  @ApiModelProperty("提示信息")
  private List<String> tipMsgs = new ArrayList<>();

  /**
   * 是否存在错误
   */
  @ApiModelProperty("是否存在错误")
  private Boolean hasError = Boolean.FALSE;

  /**
   * 业务完成
   */
  @ApiModelProperty("业务完成")
  private Boolean finished = Boolean.FALSE;

  /**
   * 数据
   */
  @ApiModelProperty("数据")
  private Object data;
}
