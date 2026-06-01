package com.lframework.starter.web.inner.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.ParameterNotFoundException;
import com.lframework.starter.common.utils.Assert;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.config.properties.SecretProperties;
import com.lframework.starter.web.core.annotations.oplog.OpLog;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.EncryptUtil;
import com.lframework.starter.web.core.utils.OpLogUtil;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.inner.components.oplog.SystemOpLogType;
import com.lframework.starter.web.inner.entity.SysParameter;
import com.lframework.starter.web.inner.mappers.system.SysParameterMapper;
import com.lframework.starter.web.inner.service.SysConfService;
import com.lframework.starter.web.inner.service.system.SysParameterService;
import com.lframework.starter.web.inner.vo.system.parameter.CreateSysParameterVo;
import com.lframework.starter.web.inner.vo.system.parameter.QuerySysParameterVo;
import com.lframework.starter.web.inner.vo.system.parameter.UpdateSysParameterVo;
import java.io.Serializable;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysParameterServiceImpl extends
    BaseMpServiceImpl<SysParameterMapper, SysParameter> implements SysParameterService,
    SysConfService {

  @Override
  public PageResult<SysParameter> query(Integer pageIndex, Integer pageSize,
      QuerySysParameterVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);
    List<SysParameter> datas = this.query(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Override
  public List<SysParameter> query(QuerySysParameterVo vo) {

    return getBaseMapper().query(vo);
  }

  @Cacheable(value = SysParameter.CACHE_NAME, key = "@cacheVariables.tenantId() + #id", unless = "#result == null")
  @Override
  public SysParameter findById(Long id) {

    return getBaseMapper().selectById(id);
  }

  @Cacheable(value = SysParameter.CACHE_NAME, key = "@cacheVariables.tenantId() + #key", unless = "#result == null")
  @Override
  public String findByKey(String key) {

    Wrapper<SysParameter> queryWrapper = Wrappers.lambdaQuery(SysParameter.class)
        .eq(SysParameter::getPmKey, key);
    SysParameter data = getBaseMapper().selectOne(queryWrapper);

    return data == null ? null : resolveFindValue(data);
  }

  @Override
  public String findRequiredByKey(String key) throws ParameterNotFoundException {
    SysConfService thisService = getThis(this.getClass());
    String data = thisService.findByKey(key);
    if (data == null) {
      throw new ParameterNotFoundException();
    }

    return data;
  }

  @Override
  public String findByKey(String key, String defaultValue) {
    SysConfService thisService = getThis(this.getClass());
    String data = thisService.findByKey(key);
    if (data == null) {
      return defaultValue;
    }

    return data;
  }

  @OpLog(type = SystemOpLogType.class, name = "新增系统参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public Long create(CreateSysParameterVo vo) {

    Wrapper<SysParameter> checkWrapper = Wrappers.lambdaQuery(SysParameter.class)
        .eq(SysParameter::getPmKey, vo.getPmKey());
    if (this.count(checkWrapper) > 0) {
      throw new DefaultClientException("键重复，请重新输入！");
    }
    SysParameter data = new SysParameter();
    data.setPmKey(vo.getPmKey());
    data.setIsEncrypt(vo.getIsEncrypt());
    data.setPmValue(resolveCreatedValue(vo));
    if (!StringUtil.isBlank(vo.getDescription())) {
      data.setDescription(vo.getDescription());
    }

    getBaseMapper().insert(data);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(resolveCreateOpLogExtra(vo));

    return data.getId();
  }

  @OpLog(type = SystemOpLogType.class, name = "修改系统参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void update(UpdateSysParameterVo vo) {

    SysParameter data = getBaseMapper().selectById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("系统参数不存在！");
    }

    LambdaUpdateWrapper<SysParameter> updateWrapper = Wrappers.lambdaUpdate(SysParameter.class)
        .set(SysParameter::getPmValue, resolveUpdatedValue(data, vo))
        .set(SysParameter::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription())
        .eq(SysParameter::getId, vo.getId());

    getBaseMapper().update(updateWrapper);

    OpLogUtil.setVariable("id", String.valueOf(data.getId()));
    OpLogUtil.setExtra(resolveUpdateOpLogExtra(data, vo));
  }

  private String resolveUpdatedValue(SysParameter data, UpdateSysParameterVo vo) {

    return resolveUpdatedValue(data, vo, null);
  }

  private String resolveCreatedValue(CreateSysParameterVo vo) {

    return resolveCreatedValue(vo, null);
  }

  private String resolveCreatedValue(CreateSysParameterVo vo, SecretProperties secretProperties) {

    String value = StringUtil.isEmpty(vo.getPmValue()) ? null : vo.getPmValue();
    return encryptValue(value, vo.getIsEncrypt(), secretProperties);
  }

  private String resolveFindValue(SysParameter data) {

    return resolveFindValue(data, null);
  }

  private String resolveFindValue(SysParameter data, SecretProperties secretProperties) {

    if (data == null) {
      return null;
    }

    return decryptValue(data.getPmValue(), data.getIsEncrypt(), secretProperties);
  }

  private String resolveUpdatedValue(SysParameter data, UpdateSysParameterVo vo,
      SecretProperties secretProperties) {

    if (Boolean.TRUE.equals(data.getIsEncrypt()) && StringUtil.isBlank(vo.getPmValue())) {
      return data.getPmValue();
    }

    String value = StringUtil.isBlank(vo.getPmValue()) ? null : vo.getPmValue();
    return encryptValue(value, data.getIsEncrypt(), secretProperties);
  }

  private CreateSysParameterVo resolveCreateOpLogExtra(CreateSysParameterVo vo) {

    CreateSysParameterVo result = new CreateSysParameterVo();
    result.setTenantId(vo.getTenantId());
    result.setPmKey(vo.getPmKey());
    result.setPmValue(Boolean.TRUE.equals(vo.getIsEncrypt()) ? StringPool.ENCRYPT_MASK
        : vo.getPmValue());
    result.setIsEncrypt(vo.getIsEncrypt());
    result.setDescription(vo.getDescription());
    return result;
  }

  private UpdateSysParameterVo resolveUpdateOpLogExtra(SysParameter data, UpdateSysParameterVo vo) {

    UpdateSysParameterVo result = new UpdateSysParameterVo();
    result.setTenantId(vo.getTenantId());
    result.setId(vo.getId());
    result.setPmValue(Boolean.TRUE.equals(data.getIsEncrypt()) ? StringPool.ENCRYPT_MASK
        : vo.getPmValue());
    result.setDescription(vo.getDescription());
    return result;
  }

  private String encryptValue(String value, Boolean isEncrypt,
      SecretProperties secretProperties) {

    if (value == null || !Boolean.TRUE.equals(isEncrypt)) {
      return value;
    }

    return secretProperties == null ? EncryptUtil.encrypt(value)
        : EncryptUtil.encrypt(value, secretProperties);
  }

  private String decryptValue(String value, Boolean isEncrypt,
      SecretProperties secretProperties) {

    if (value == null || !Boolean.TRUE.equals(isEncrypt)) {
      return value;
    }

    return secretProperties == null ? EncryptUtil.decrypt(value)
        : EncryptUtil.decrypt(value, secretProperties);
  }

  @OpLog(type = SystemOpLogType.class, name = "删除系统参数，ID：{}", params = {"#id"})
  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteById(Long id) {

    getBaseMapper().deleteById(id);
  }

  @Override
  public Boolean getBoolean(String key) {
    SysConfService thisService = getThis(getClass());
    String value = thisService.findByKey(key);
    return value == null ? null : "true".equalsIgnoreCase(value);
  }

  @Override
  public Boolean getBoolean(String key, Boolean defaultValue) {
    Boolean value = getBoolean(key);
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @Override
  public Integer getInteger(String key) {
    SysConfService thisService = getThis(getClass());
    String value = thisService.findByKey(key);
    try {
      return value == null ? null : Integer.valueOf(value);
    } catch (NumberFormatException e) {
      // 转换失败
      return null;
    }
  }

  @Override
  public Integer getInteger(String key, Integer defaultValue) {
    Integer value = getInteger(key);
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @Override
  public Long getLong(String key) {
    SysConfService thisService = getThis(getClass());
    String value = thisService.findByKey(key);
    try {
      return value == null ? null : Long.valueOf(value);
    } catch (NumberFormatException e) {
      // 转换失败
      return null;
    }
  }

  @Override
  public Long getLong(String key, Long defaultValue) {
    Long value = getLong(key);
    if (value == null) {
      return defaultValue;
    }

    return value;
  }

  @CacheEvict(value = SysParameter.CACHE_NAME, key = "@cacheVariables.tenantId() + #key")
  @Override
  public void cleanCacheByKey(Serializable key) {

  }
}
