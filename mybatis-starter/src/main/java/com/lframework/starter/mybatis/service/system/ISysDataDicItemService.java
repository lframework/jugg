package com.lframework.starter.mybatis.service.system;

import com.lframework.starter.mybatis.entity.SysDataDicItem;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.BaseMpService;
import com.lframework.starter.mybatis.vo.system.dic.item.CreateSysDataDicItemVo;
import com.lframework.starter.mybatis.vo.system.dic.item.QuerySysDataDicItemVo;
import com.lframework.starter.mybatis.vo.system.dic.item.UpdateSysDataDicItemVo;
import java.util.List;

/**
 * 数据字典值 Service
 *
 * @author zmj
 */
public interface ISysDataDicItemService extends BaseMpService<SysDataDicItem> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysDataDicItem> query(Integer pageIndex, Integer pageSize, QuerySysDataDicItemVo vo);

  /**
   * 查询列表
   *
   * @return
   */
  List<SysDataDicItem> query(QuerySysDataDicItemVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysDataDicItem findById(String id);

  /**
   * 根据字典Code、字典值Code查询
   *
   * @param dicCode
   * @param code
   * @return
   */
  SysDataDicItem findByCode(String dicCode, String code);

  /**
   * 根据字典编号查询
   *
   * @param dicCode
   * @return
   */
  List<SysDataDicItem> findByDicCode(String dicCode);

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateSysDataDicItemVo vo);

  /**
   * 修改
   *
   * @param vo
   */
  void update(UpdateSysDataDicItemVo vo);

  /**
   * 根据ID删除
   *
   * @param id
   */
  void deleteById(String id);
}
