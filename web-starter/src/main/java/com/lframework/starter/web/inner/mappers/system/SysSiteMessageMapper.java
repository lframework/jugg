package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import com.lframework.starter.web.inner.vo.system.message.site.QuerySysSiteMessageByUserVo;
import com.lframework.starter.web.inner.vo.system.message.site.QuerySysSiteMessageVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 站内信 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysSiteMessageMapper extends BaseMapper<SysSiteMessage> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysSiteMessage> query(@Param("vo") QuerySysSiteMessageVo vo);

  /**
   * 根据用户查询
   *
   * @param vo
   * @return
   */
  List<SysSiteMessage> queryByUser(@Param("vo") QuerySysSiteMessageByUserVo vo);
}
