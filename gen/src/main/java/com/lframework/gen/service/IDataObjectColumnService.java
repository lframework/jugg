package com.lframework.gen.service;

import com.lframework.gen.dto.dataobj.GenDataObjectColumnDto;
import com.lframework.gen.entity.GenDataObjectColumn;
import com.lframework.gen.vo.dataobj.CreateDataObjectColumnVo;
import com.lframework.gen.vo.dataobj.UpdateDataObjectColumnGenerateVo;
import com.lframework.starter.mybatis.service.BaseMpService;
import java.util.List;

public interface IDataObjectColumnService extends BaseMpService<GenDataObjectColumn> {

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  String create(CreateDataObjectColumnVo vo);

  /**
   * 根据DataObjId删除
   *
   * @param dataObjId
   */
  void deleteByDataObjId(String dataObjId);

  /**
   * 根据数据对象查询
   *
   * @param dataObjId
   * @return
   */
  List<GenDataObjectColumnDto> getByDataObjId(String dataObjId);

  /**
   * 修改生成器配置
   *
   * @param dataObjId
   * @param vo
   */
  void updateGenerate(String dataObjId, List<UpdateDataObjectColumnGenerateVo> vo);
}
