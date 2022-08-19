package com.lframework.starter.mybatis.impl.message;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.message.SysNoticeTaskDto;
import com.lframework.starter.mybatis.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.message.ISysNoticeTaskService;
import com.lframework.starter.mybatis.service.system.ISysNoticeService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.notice.QuerySysNoticeByUserVo;
import com.lframework.web.common.security.SecurityUtil;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zmj
 * @since 2022/8/19
 */
@Service
public class SysNoticeTaskServiceImpl implements ISysNoticeTaskService {

  @Autowired
  private ISysNoticeService sysNoticeService;

  @Override
  public String getType() {
    return "notice";
  }

  @Override
  public PageResult<SysNoticeTaskDto> queryList() {
    QuerySysNoticeByUserVo vo = new QuerySysNoticeByUserVo();
    vo.setUserId(SecurityUtil.getCurrentUser().getId());
    vo.setReaded(Boolean.FALSE);

    PageResult<QuerySysNoticeByUserDto> pageResult = sysNoticeService.queryByUser(1, 5, vo);
    List<QuerySysNoticeByUserDto> datas = pageResult.getDatas();
    List<SysNoticeTaskDto> results = CollectionUtil.isEmpty(datas) ? Collections.EMPTY_LIST
        : datas.stream().map(SysNoticeTaskDto::new).collect(
            Collectors.toList());

    return PageResultUtil.rebuild(pageResult, results);
  }

  @Override
  public SysNoticeTaskDto findById(Serializable id) {
    return null;
  }
}
