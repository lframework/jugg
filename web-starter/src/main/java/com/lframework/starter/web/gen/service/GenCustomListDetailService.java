package com.lframework.starter.web.gen.service;

import com.lframework.starter.web.gen.entity.GenCustomListDetail;
import com.lframework.starter.web.core.service.BaseMpService;
import java.util.List;

public interface GenCustomListDetailService extends BaseMpService<GenCustomListDetail> {

  /**
   * 根据自定义列表ID查询
   * @param customListId
   * @return
   */
  List<GenCustomListDetail> getByCustomListId(String customListId);

  /**
   * 根据自定义列表ID删除
   * @param customListId
   */
  void deleteByCustomListId(String customListId);

}
