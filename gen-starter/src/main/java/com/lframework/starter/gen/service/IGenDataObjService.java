package com.lframework.starter.gen.service;

import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.vo.data.obj.CreateGenDataObjVo;
import com.lframework.starter.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.gen.vo.data.obj.UpdateGenDataObjVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IGenDataObjService extends BaseMpService<GenDataObj> {

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
   * 根据ID批量删除
   *
   * @param ids
   */
  void batchDelete(List<String> ids);

  /**
   * 批量启用
   *
   * @param ids
   */
  void batchEnable(List<String> ids);

  /**
   * 批量停用
   *
   * @param ids
   */
  void batchUnable(List<String> ids);
}
