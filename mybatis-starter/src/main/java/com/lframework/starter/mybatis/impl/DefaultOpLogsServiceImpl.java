package com.lframework.starter.mybatis.impl;

import com.github.pagehelper.PageInfo;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.dto.DefaultOpLogsDto;
import com.lframework.starter.mybatis.entity.DefaultOpLogs;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.mappers.DefaultOpLogsMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IOpLogsService;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.CreateOpLogsVo;
import com.lframework.starter.mybatis.vo.QueryOpLogsVo;
import com.lframework.starter.web.utils.EnumUtil;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

/**
 * 操作日志Service实现
 *
 * @author zmj
 */
@Slf4j
public class DefaultOpLogsServiceImpl extends
    BaseMpServiceImpl<DefaultOpLogsMapper, DefaultOpLogs> implements IOpLogsService {

  @Transactional
  @Override
  public String create(CreateOpLogsVo vo) {

    DefaultOpLogs record = this.doCreate(vo);

    return record.getId();
  }

  @Override
  public PageResult<DefaultOpLogsDto> query(Integer pageIndex, Integer pageSize, QueryOpLogsVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultOpLogsDto> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public DefaultOpLogsDto getById(String id) {

    return this.doGetById(id);
  }

  @Transactional
  @Override
  public void clearLogs(LocalDateTime endTime) {

    log.info("开始清除创建时间早于{}的操作日志", endTime);

    this.doClearLogs(endTime);
  }

  protected DefaultOpLogs doCreate(CreateOpLogsVo vo) {

    DefaultOpLogs record = new DefaultOpLogs();
    record.setId(IdUtil.getId());
    record.setName(vo.getName());
    record.setLogType(EnumUtil.getByCode(OpLogType.class, vo.getLogType()));
    if (!StringUtil.isBlank(vo.getCreateBy())) {
      record.setCreateBy(vo.getCreateBy());
    }

    if (!StringUtil.isBlank(vo.getExtra())) {
      record.setExtra(vo.getExtra());
    }
    record.setIp(vo.getIp());

    getBaseMapper().insert(record);

    return record;
  }

  protected List<DefaultOpLogsDto> doQuery(QueryOpLogsVo vo) {

    return getBaseMapper().query(vo);
  }

  protected DefaultOpLogsDto doGetById(String id) {

    return getBaseMapper().getById(id);
  }

  protected void doClearLogs(LocalDateTime endTime) {

    getBaseMapper().clearLogs(endTime);
  }
}
