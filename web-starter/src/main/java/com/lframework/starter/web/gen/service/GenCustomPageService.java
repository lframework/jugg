package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.gen.entity.GenCustomPage;
import com.lframework.starter.web.gen.vo.custom.page.CreateGenCustomPageVo;
import com.lframework.starter.web.gen.vo.custom.page.GenCustomPageSelectorVo;
import com.lframework.starter.web.gen.vo.custom.page.QueryGenCustomPageVo;
import com.lframework.starter.web.gen.vo.custom.page.UpdateGenCustomPageVo;
import java.util.List;

public interface GenCustomPageService extends BaseMpService<GenCustomPage> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomPage> query(Integer pageIndex, Integer pageSize,
      QueryGenCustomPageVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<GenCustomPage> query(QueryGenCustomPageVo vo);

  /**
   * 选择器
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<GenCustomPage> selector(Integer pageIndex, Integer pageSize,
      GenCustomPageSelectorVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  GenCustomPage findById(Integer id);

  /**
   * 新增
   *
   * @param data
   * @return
   */
  Integer create(CreateGenCustomPageVo data);

  /**
   * 修改
   *
   * @param data
   */
  void update(UpdateGenCustomPageVo data);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void delete(Integer id);
}
