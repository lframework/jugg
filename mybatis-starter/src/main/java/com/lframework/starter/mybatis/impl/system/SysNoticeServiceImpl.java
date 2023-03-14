package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.ThreadUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.system.notice.QuerySysNoticeByUserDto;
import com.lframework.starter.mybatis.dto.system.notice.SysNoticeDto;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.entity.SysNotice;
import com.lframework.starter.mybatis.entity.SysNoticeLog;
import com.lframework.starter.mybatis.enums.DefaultOpLogType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.SysNoticeMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.SysNoticeLogService;
import com.lframework.starter.mybatis.service.system.SysNoticeService;
import com.lframework.starter.mybatis.service.system.SysUserService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.notice.CreateSysNoticeVo;
import com.lframework.starter.mybatis.vo.system.notice.QuerySysNoticeByUserVo;
import com.lframework.starter.mybatis.vo.system.notice.QuerySysNoticeVo;
import com.lframework.starter.mybatis.vo.system.notice.UpdateSysNoticeVo;
import com.lframework.starter.mybatis.vo.system.user.QuerySysUserVo;
import com.lframework.starter.web.utils.IdUtil;
import com.lframework.starter.web.common.security.SecurityUtil;
import com.lframework.starter.web.common.threads.DefaultRunnable;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysNoticeServiceImpl extends BaseMpServiceImpl<SysNoticeMapper, SysNotice> implements
    SysNoticeService {

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private SysNoticeLogService sysNoticeLogService;

  @Override
  public PageResult<SysNotice> query(Integer pageIndex, Integer pageSize, QuerySysNoticeVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysNotice> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysNotice> query(QuerySysNoticeVo vo) {

    return getBaseMapper().query(vo);
  }

  @Override
  public PageResult<QuerySysNoticeByUserDto> queryByUser(Integer pageIndex, Integer pageSize,
      QuerySysNoticeByUserVo vo) {
    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<QuerySysNoticeByUserDto> datas = getBaseMapper().queryByUser(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = SysNotice.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysNoticeDto getContent(String id) {
    SysNotice record = getBaseMapper().selectById(id);
    if (record == null) {
      return null;
    }

    return new SysNoticeDto(record);
  }

  @Override
  public SysNotice findById(String id) {

    return getBaseMapper().selectById(id);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "新增系统通知，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public String create(CreateSysNoticeVo vo) {

    SysNotice data = new SysNotice();
    data.setId(IdUtil.getId());
    data.setTitle(vo.getTitle());
    data.setContent(vo.getContent());
    data.setPublished(vo.getPublished());

    getBaseMapper().insert(data);

    if (vo.getPublished()) {
      ThreadUtil.execAsync(new DefaultRunnable(() -> {
        SysNoticeService thisService = getThis(getClass());
        thisService.publish(data.getId());
      }));
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);

    return data.getId();
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "修改系统通知，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysNoticeVo vo) {

    SysNotice data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("系统通知不存在！");
    }

    LambdaUpdateWrapper<SysNotice> updateWrapper = Wrappers.lambdaUpdate(SysNotice.class)
        .set(SysNotice::getTitle, vo.getTitle())
        .set(SysNotice::getContent, vo.getContent())
        .set(SysNotice::getAvailable, vo.getAvailable())
        .set(SysNotice::getPublished, vo.getPublished())
        .set(SysNotice::getPublishTime, null)
        .set(SysNotice::getReadedNum, 0)
        .set(SysNotice::getUnReadNum, 0)
        .eq(SysNotice::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    // 无论发布还是不发布 都要删除
    Wrapper<SysNoticeLog> deleteLogWrapper = Wrappers.lambdaQuery(SysNoticeLog.class)
            .eq(SysNoticeLog::getNoticeId, data.getId());
    sysNoticeLogService.remove(deleteLogWrapper);

    if (vo.getPublished()) {
      ThreadUtil.execAsync(new DefaultRunnable(() -> {
        SysNoticeService thisService = getThis(getClass());
        thisService.publish(data.getId());
      }));
    }

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = DefaultOpLogType.OTHER, name = "发布系统通知，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void publish(String id) {

    // 查询所有用户
    QuerySysUserVo querySysUserVo = new QuerySysUserVo();
    querySysUserVo.setAvailable(true);
    List<DefaultSysUser> users = sysUserService.query(querySysUserVo);

    Wrapper<SysNotice> updateWrapper = Wrappers.lambdaUpdate(SysNotice.class)
        .eq(SysNotice::getId, id).set(SysNotice::getReadedNum, 0)
        .set(SysNotice::getUnReadNum, users.size()).set(SysNotice::getPublished, Boolean.TRUE)
        .set(SysNotice::getPublishTime,
            LocalDateTime.now());
    this.update(updateWrapper);

    Wrapper<SysNoticeLog> deleteLogWrapper = Wrappers.lambdaQuery(SysNoticeLog.class)
        .eq(SysNoticeLog::getNoticeId, id);
    sysNoticeLogService.remove(deleteLogWrapper);

    if (!CollectionUtil.isEmpty(users)) {
      List<SysNoticeLog> logs = users.stream().map(t -> {
        SysNoticeLog log = new SysNoticeLog();
        log.setId(IdUtil.getId());
        log.setNoticeId(id);
        log.setUserId(t.getId());
        log.setReaded(Boolean.FALSE);

        return log;
      }).collect(Collectors.toList());

      sysNoticeLogService.saveBatch(logs);
    }
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void setReaded(String id, String userId) {
    if (sysNoticeLogService.setReaded(id, userId)) {
      getBaseMapper().setReaded(id);
    }
  }

  @CacheEvict(value = SysNotice.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
