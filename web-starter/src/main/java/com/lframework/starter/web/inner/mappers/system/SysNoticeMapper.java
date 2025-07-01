package com.lframework.starter.web.inner.mappers.system;

import com.lframework.starter.web.inner.entity.SysNotice;
import com.lframework.starter.web.inner.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.starter.web.core.mapper.BaseMapper;
import com.lframework.starter.web.inner.vo.system.notice.QuerySysNoticeByUserVo;
import com.lframework.starter.web.inner.vo.system.notice.QuerySysNoticeVo;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统通知 Mapper 接口
 * </p>
 *
 * @author zmj
 */
public interface SysNoticeMapper extends BaseMapper<SysNotice> {

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  List<SysNotice> query(@Param("vo") QuerySysNoticeVo vo);

  /**
   * 设置已读
   *
   * @param id
   */
  void setReaded(String id);

  /**
   * 根据用户查询
   *
   * @param pageIndex
   * @param pageSize
   * @param vo
   * @return
   */
  List<QuerySysNoticeByUserDto> queryByUser(@Param("vo") QuerySysNoticeByUserVo vo);
}
