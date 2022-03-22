package com.lframework.gen.service;

import com.lframework.gen.dto.dataobj.DataObjectDto;
import com.lframework.gen.dto.dataobj.DataObjectGenerateDto;
import com.lframework.gen.enums.DataObjectGenStatus;
import com.lframework.gen.vo.dataobj.CreateDataObjectVo;
import com.lframework.gen.vo.dataobj.QueryDataObjectVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectGenerateVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import java.util.List;

public interface IDataObjectService extends BaseService {

  /**
   * 查询数据对象列表
   *
   * @param vo
   * @return
   */
  PageResult<DataObjectDto> query(QueryDataObjectVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DataObjectDto getById(String id);

  /**
   * 创建数据对象
   *
   * @param vo
   * @return
   */
  String create(CreateDataObjectVo vo);

  /**
   * 修改数据对象
   *
   * @param vo
   */
  void update(UpdateDataObjectVo vo);

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
   * @param userId
   */
  void batchEnable(List<String> ids, String userId);

  /**
   * 批量停用
   *
   * @param ids
   * @param userId
   */
  void batchUnable(List<String> ids, String userId);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  DataObjectGenerateDto getGenerateById(String id);

  /**
   * 修改生成器配置信息
   *
   * @param vo
   */
  void updateGenerate(UpdateDataObjectGenerateVo vo);

  /**
   * 设置状态
   *
   * @param id
   * @param status
   */
  void setStatus(String id, DataObjectGenStatus status);
}
