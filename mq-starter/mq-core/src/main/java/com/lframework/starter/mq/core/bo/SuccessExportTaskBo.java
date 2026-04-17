package com.lframework.starter.mq.core.bo;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.mq.core.entity.ExportTask;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SuccessExportTaskBo extends BaseBo<ExportTask> {

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
   * 文件大小
   */
  @Schema(description = "文件大小")
  private String fileSize;

  /**
   * 上传记录ID
   */
  @Schema(description = "上传记录ID")
  private String recordId;

  /**
   * 总数据条数
   */
  @Schema(description = "总数据条数")
  private Long totalCount;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  private LocalDateTime createTime;

  /**
   * 完成时间
   */
  @Schema(description = "完成时间")
  private LocalDateTime finishTime;

  public SuccessExportTaskBo(ExportTask dto) {
    super(dto);
  }
}
