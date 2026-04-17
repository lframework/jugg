package com.lframework.starter.mq.core.bo;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.mq.core.entity.ExportTask;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExportingExportTaskBo extends BaseBo<ExportTask> {

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
   * 总数据条数
   */
  @Schema(description = "总数据条数")
  private Long totalCount;

  /**
   * 当前完成数据条数
   */
  @Schema(description = "当前完成数据条数")
  private Long curCount;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Integer status;

  public ExportingExportTaskBo(ExportTask dto) {
    super(dto);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <A> BaseBo<ExportTask> convert(ExportTask dto) {
    return super.convert(dto, ExportingExportTaskBo::getStatus);
  }

  @Override
  protected void afterInit(ExportTask dto) {
    this.status = dto.getStatus().getCode();
  }
}
