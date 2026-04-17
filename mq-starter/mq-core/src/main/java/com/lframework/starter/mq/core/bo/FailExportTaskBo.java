package com.lframework.starter.mq.core.bo;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.mq.core.entity.ExportTask;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class FailExportTaskBo extends BaseBo<ExportTask> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  /**
   * 错误信息
   */
  @Schema(description = "错误信息")
  private String errorMsg;

  public FailExportTaskBo(ExportTask dto) {
    super(dto);
  }
}
