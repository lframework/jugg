package com.lframework.starter.gen.dto.dataobj;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class DataObjectGenerateDto implements BaseDto, Serializable {

  /**
   * 字段信息
   */
  private List<GenDataObjectColumnDto> columns;

  /**
   * 基本设置
   */
  private GenGenerateInfoDto generateInfo;

  /**
   * 新增配置
   */
  private List<GenCreateColumnConfigDto> createConfigs;

  /**
   * 修改配置
   */
  private List<GenUpdateColumnConfigDto> updateConfigs;

  /**
   * 查询配置
   */
  private List<GenQueryColumnConfigDto> queryConfigs;

  /**
   * 查询参数配置
   */
  private List<GenQueryParamsColumnConfigDto> queryParamsConfigs;

  /**
   * 详情配置
   */
  private List<GenDetailColumnConfigDto> detailConfigs;
}
