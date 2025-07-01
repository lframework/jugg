package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.dto.data.entity.DataEntityGenerateDto;
import com.lframework.starter.web.gen.entity.GenDataEntity;
import com.lframework.starter.web.gen.vo.data.entity.CreateDataEntityVo;
import com.lframework.starter.web.gen.vo.data.entity.GenDataEntitySelectorVo;
import com.lframework.starter.web.gen.vo.data.entity.QueryDataEntityVo;
import com.lframework.starter.web.gen.vo.data.entity.UpdateDataEntityGenerateVo;
import com.lframework.starter.web.gen.vo.data.entity.UpdateDataEntityVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

public interface GenDataEntityService extends BaseMpService<GenDataEntity> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataEntity> query(Integer pageIndex, Integer pageSize, QueryDataEntityVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataEntity> query(QueryDataEntityVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataEntity> selector(Integer pageIndex, Integer pageSize,
      GenDataEntitySelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDataEntity findById(String id);

  /**
   * 创建
   *
   * @param data
   * @return
   */
  String create(CreateDataEntityVo data);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateDataEntityVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void delete(String id);

  /**
   * 启用
   *
   * @param id
   */
  void enable(String id);

  /**
   * 停用
   *
   * @param id
   */
  void unable(String id);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DataEntityGenerateDto getGenerateById(String id);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(UpdateDataEntityGenerateVo vo);

  /**
   * 同步数据表
   *
   * @param id
   */
  void syncTable(String id);

}
