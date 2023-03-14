package com.lframework.starter.mybatis.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.mybatis.mappers.DefaultOpLogsMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.OpLogsService;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import com.lframework.starter.web.utils.IdUtil;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志Service实现
 *
 * @author zmj
 */
@Slf4j
public class DefaultOpLogsServiceImpl extends BaseMpServiceImpl<DefaultOpLogsMapper, DefaultOpLogs>
    implements OpLogsService {

  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateOpLogsVo vo) {

    DefaultOpLogs record = this.doCreate(vo);

    this.save(record);

    return record.getId();
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void create(Collection<CreateOpLogsVo> list) {
    if (CollectionUtil.isEmpty(list)) {
      return;
    }
    List<DefaultOpLogs> records = list.stream().map(this::doCreate).collect(Collectors.toList());
    this.saveBatch(records);
  }

  @Override
  public PageResult<DefaultOpLogs> query(Integer pageIndex, Integer pageSize, QueryOpLogsVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultOpLogs> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public DefaultOpLogs findById(String id) {

    return this.doGetById(id);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void clearLogs(LocalDateTime endTime) {

    log.info("开始清除创建时间早于{}的操作日志", endTime);

    this.doClearLogs(endTime);
  }

  protected DefaultOpLogs doCreate(CreateOpLogsVo vo) {

    DefaultOpLogs record = new DefaultOpLogs();
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

  protected List<DefaultOpLogs> doQuery(QueryOpLogsVo vo) {

    return getBaseMapper().query(vo);
  }

  protected DefaultOpLogs doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected void doClearLogs(LocalDateTime endTime) {

    getBaseMapper().clearLogs(endTime);
  }
}
