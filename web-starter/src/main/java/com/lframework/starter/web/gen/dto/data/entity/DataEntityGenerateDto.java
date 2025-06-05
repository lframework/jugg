package com.lframework.starter.web.gen.dto.data.entity;

import com.lframework.starter.web.gen.dto.gen.GenCreateColumnConfigDto;
import com.lframework.starter.web.gen.dto.gen.GenDetailColumnConfigDto;
import com.lframework.starter.web.gen.dto.gen.GenGenerateInfoDto;
import com.lframework.starter.web.gen.dto.gen.GenQueryColumnConfigDto;
import com.lframework.starter.web.gen.dto.gen.GenQueryParamsColumnConfigDto;
import com.lframework.starter.web.gen.dto.gen.GenUpdateColumnConfigDto;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.core.dto.BaseDto;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
public class DataEntityGenerateDto implements BaseDto, Serializable {

  /**
   * 字段信息
   */
  private List<GenDataEntityDetail> columns;

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
