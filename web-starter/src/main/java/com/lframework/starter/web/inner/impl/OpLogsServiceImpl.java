package com.lframework.starter.web.inner.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.inner.entity.OpLogs;
import com.lframework.starter.web.inner.mappers.OpLogsMapper;
import com.lframework.starter.web.inner.service.OpLogsService;
import com.lframework.starter.web.inner.vo.oplogs.CreateOpLogsVo;
import com.lframework.starter.web.inner.vo.oplogs.QueryOpLogsVo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志Service实现
 *
 * @author zmj
 */
@Slf4j
@Service
public class OpLogsServiceImpl extends BaseMpServiceImpl<OpLogsMapper, OpLogs>
    implements OpLogsService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateOpLogsVo vo) {

    OpLogs record = this.doCreate(vo);

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void create(Collection<CreateOpLogsVo> list) {
    if (CollectionUtil.isEmpty(list)) {
      return;
    }
    List<OpLogs> records = list.stream().map(this::doCreate).collect(Collectors.toList());
    this.saveBatch(records);
  }

  @Override
  public PageResult<OpLogs> query(Integer pageIndex, Integer pageSize, QueryOpLogsVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<OpLogs> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public OpLogs findById(String id) {

    return this.doGetById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void clearLogs(LocalDateTime endTime) {

    log.info("开始清除创建时间早于{}的操作日志", endTime);

    this.doClearLogs(endTime);
  }

  protected OpLogs doCreate(CreateOpLogsVo vo) {

    OpLogs record = new OpLogs();
    record.setId(IdUtil.getId());
    record.setName(vo.getName());
    record.setLogType(vo.getLogType());
    if (!StringUtil.isBlank(vo.getCreateBy())) {
      record.setCreateBy(vo.getCreateBy());
    }

    if (!StringUtil.isBlank(vo.getCreateById())) {
      record.setCreateById(vo.getCreateById());
    }

    if (!StringUtil.isBlank(vo.getExtra())) {
      record.setExtra(vo.getExtra());
    }
    record.setIp(vo.getIp());

    return record;
  }

  protected List<OpLogs> doQuery(QueryOpLogsVo vo) {

    return getBaseMapper().query(vo);
  }

  protected OpLogs doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected void doClearLogs(LocalDateTime endTime) {

    getBaseMapper().clearLogs(endTime);
  }
}
