package com.lframework.starter.web.inner.service.system;

import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.SysMailMessage;
import com.lframework.starter.web.inner.vo.system.message.mail.QuerySysMailMessageVo;
import java.util.List;

public interface SysMailMessageService extends BaseMpService<SysMailMessage> {

  /**
   * 查询列表
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  PageResult<SysMailMessage> query(Integer pageIndex, Integer pageSize, QuerySysMailMessageVo vo);

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysMailMessage> query(QuerySysMailMessageVo vo);

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  SysMailMessage findById(String id);
}
