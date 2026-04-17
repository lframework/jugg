package com.lframework.starter.web.core.components.resp;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.Data;

/**
 * 统一分页数据
 *
 * @param <T>
 * @author zmj
 */
@Data
public class PageResult<T> implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 是否有上一页
   */
  @Schema(description = "是否有上一页")
  private boolean hasPrev;

  /**
   * 是否有下一页
   */
  @Schema(description = "是否有下一页")
  private boolean hasNext;

  /**
   * 总记录数
   */
  @Schema(description = "总记录数")
  private long totalCount;

  /**
   * 每页数据量
   */
  @Schema(description = "每页条数")
  private long pageSize;

  /**
   * 第几页
   */
  @Schema(description = "当前页码")
  private long pageIndex;

  /**
   * 总共几页
   */
  @Schema(description = "总页数")
  private int totalPage;

  /**
   * 数据
   */
  @Schema(description = "数据")
  private List<T> datas;

  /**
   * 附加数据
   */
  @Schema(description = "附加数据")
  private Map<Object, Object> extra;
}
