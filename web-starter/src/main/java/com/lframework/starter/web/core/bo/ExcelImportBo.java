package com.lframework.starter.web.core.bo;

import com.lframework.starter.web.core.dto.VoidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
public class ExcelImportBo extends BaseBo<VoidDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 当前进度（条数）
   */
  @Schema(description = "当前进度（条数）")
  private Integer process = 0;

  /**
   * 成功进度（条数）
   */
  @Schema(description = "成功进度（条数）")
  private Integer successProcess = 0;

  /**
   * 提示信息
   */
  @Schema(description = "提示信息")
  private List<String> tipMsgs = new ArrayList<>();

  /**
   * 是否存在错误
   */
  @Schema(description = "是否存在错误")
  private Boolean hasError = Boolean.FALSE;

  /**
   * 业务完成
   */
  @Schema(description = "业务完成")
  private Boolean finished = Boolean.FALSE;

  /**
   * 数据
   */
  @Schema(description = "数据")
  private Object data;
}
