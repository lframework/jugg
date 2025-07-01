package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.entity.GenDataObj;
import com.lframework.starter.web.gen.vo.data.obj.CreateGenDataObjVo;
import com.lframework.starter.web.gen.vo.data.obj.GenDataObjSelectorVo;
import com.lframework.starter.web.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.web.gen.vo.data.obj.UpdateGenDataObjVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

public interface GenDataObjService extends BaseMpService<GenDataObj> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataObj> query(Integer pageIndex, Integer pageSize, QueryGenDataObjVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenDataObj> query(QueryGenDataObjVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenDataObj> selector(Integer pageIndex, Integer pageSize, GenDataObjSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenDataObj findById(String id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  String create(CreateGenDataObjVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenDataObjVo data);

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
   * 查询所有关联了数据实体的数据对象ID
   * @return
   */
  List<String> getRelaGenDataEntityIds(String entityId);
}
