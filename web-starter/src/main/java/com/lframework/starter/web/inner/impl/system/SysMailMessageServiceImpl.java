package com.lframework.starter.web.inner.impl.system;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.entity.SysMailMessage;
import com.lframework.starter.web.inner.mappers.system.SysMailMessageMapper;
import com.lframework.starter.web.inner.service.system.SysMailMessageService;
import com.lframework.starter.web.inner.vo.system.message.mail.QuerySysMailMessageVo;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class SysMailMessageServiceImpl extends
    BaseMpServiceImpl<SysMailMessageMapper, SysMailMessage>
    implements SysMailMessageService {

  @Override
  public PageResult<SysMailMessage> query(Integer pageIndex, Integer pageSize,
      QuerySysMailMessageVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysMailMessage> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysMailMessage> query(QuerySysMailMessageVo vo) {
    return getBaseMapper().query(vo);
  }

  @Override
  public SysMailMessage findById(String id) {
    return getById(id);
  }
}
