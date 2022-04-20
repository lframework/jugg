package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.constants.PatternPool;
import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.RegUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.mybatis.entity.DefaultSysUser;
import com.lframework.starter.mybatis.enums.Gender;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.events.UpdateUserEvent;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.DefaultSysUserMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysUserDeptService;
import com.lframework.starter.mybatis.service.system.ISysUserPositionService;
import com.lframework.starter.mybatis.service.system.ISysUserRoleService;
import com.lframework.starter.mybatis.service.system.ISysUserService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dept.SysUserDeptSettingVo;
import com.lframework.starter.mybatis.vo.system.position.SysUserPositionSettingVo;
import com.lframework.starter.mybatis.vo.system.user.CreateSysUserVo;
import com.lframework.starter.mybatis.vo.system.user.QuerySysUserVo;
import com.lframework.starter.mybatis.vo.system.user.RegistUserVo;
import com.lframework.starter.mybatis.vo.system.user.SysUserRoleSettingVo;
import com.lframework.starter.mybatis.vo.system.user.SysUserSelectorVo;
import com.lframework.starter.mybatis.vo.system.user.UpdateSysUserVo;
import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.impl.AbstractFlowGenerator;
import com.lframework.starter.web.components.security.PasswordEncoderWrapper;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.dto.UserInfoDto;
import com.lframework.starter.web.service.IGenerateCodeService;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

public class DefaultSysUserServiceImpl extends
    BaseMpServiceImpl<DefaultSysUserMapper, DefaultSysUser>
    implements ISysUserService, ApplicationListener<UpdateUserEvent> {

  @Autowired
  private PasswordEncoderWrapper encoderWrapper;

  @Autowired
  private ISysUserPositionService sysUserPositionService;

  @Autowired
  private ISysUserDeptService sysUserDeptService;

  @Autowired
  private ISysUserRoleService sysUserRoleService;

  @Autowired
  private IGenerateCodeService generateCodeService;

  @Override
  public PageResult<DefaultSysUserDto> query(Integer pageIndex, Integer pageSize,
      QuerySysUserVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultSysUserDto> datas = this.doQuery(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Cacheable(value = DefaultSysUserDto.CACHE_NAME, key = "#id", unless = "#result == null")
  @Override
  public DefaultSysUserDto findById(String id) {

    return this.doGetById(id);
  }

  @OpLog(type = OpLogType.OTHER, name = "启用用户，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchEnable(List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchEnable(ids);

    ISysUserService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "停用用户，ID：{}", params = "#ids", loopFormat = true)
  @Transactional
  @Override
  public void batchUnable(List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return;
    }

    this.doBatchUnable(ids);

    ISysUserService thisService = getThis(this.getClass());
    for (String id : ids) {
      thisService.cleanCacheByKey(id);
    }
  }

  @OpLog(type = OpLogType.OTHER, name = "新增用户，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public String create(CreateSysUserVo vo) {

    DefaultSysUser record = this.doCreate(vo);

    SysUserPositionSettingVo positionSettingVo = new SysUserPositionSettingVo();
    positionSettingVo.setUserId(record.getId());
    positionSettingVo.setPositionIds(vo.getPositionIds());
    sysUserPositionService.setting(positionSettingVo);

    SysUserDeptSettingVo deptSettingVo = new SysUserDeptSettingVo();
    deptSettingVo.setUserId(record.getId());
    deptSettingVo.setDeptIds(vo.getDeptIds());
    sysUserDeptService.setting(deptSettingVo);

    SysUserRoleSettingVo roleSettingVo = new SysUserRoleSettingVo();
    roleSettingVo.setUserIds(Collections.singletonList(record.getId()));
    roleSettingVo.setRoleIds(vo.getRoleIds());
    sysUserRoleService.setting(roleSettingVo);

    OpLogUtil.setVariable("id", record.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    return record.getId();
  }

  @OpLog(type = OpLogType.OTHER, name = "修改用户，ID：{}, 编号：{}", params = {"#id", "#code"})
  @Transactional
  @Override
  public void update(UpdateSysUserVo vo) {

    DefaultSysUserDto data = this.findById(vo.getId());
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("用户不存在！");
    }

    if (!StringUtil.isBlank(vo.getPassword())) {
      if (!RegUtil.isMatch(PatternPool.PATTERN_PASSWORD, vo.getPassword())) {
        throw new DefaultClientException("密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线！");
      }
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      if (!RegUtil.isMatch(PatternPool.PATTERN_CN_TEL, vo.getTelephone())) {
        throw new DefaultClientException("联系电话格式不正确！");
      }
    }

    this.doUpdate(vo);

    SysUserPositionSettingVo positionSettingVo = new SysUserPositionSettingVo();
    positionSettingVo.setUserId(vo.getId());
    positionSettingVo.setPositionIds(vo.getPositionIds());

    sysUserPositionService.setting(positionSettingVo);

    SysUserDeptSettingVo deptSettingVo = new SysUserDeptSettingVo();
    deptSettingVo.setUserId(vo.getId());
    deptSettingVo.setDeptIds(vo.getDeptIds());
    sysUserDeptService.setting(deptSettingVo);

    SysUserRoleSettingVo roleSettingVo = new SysUserRoleSettingVo();
    roleSettingVo.setUserIds(Collections.singletonList(vo.getId()));
    roleSettingVo.setRoleIds(vo.getRoleIds());
    sysUserRoleService.setting(roleSettingVo);

    OpLogUtil.setVariable("id", data.getId());
    OpLogUtil.setVariable("code", vo.getCode());
    OpLogUtil.setExtra(vo);

    ISysUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(data.getId());
  }

  @Override
  public PageResult<DefaultSysUserDto> selector(Integer pageIndex, Integer pageSize,
      SysUserSelectorVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    PageHelperUtil.startPage(pageIndex, pageSize);

    List<DefaultSysUserDto> datas = this.doSelector(vo);

    return PageResultUtil.convert(new PageInfo<>(datas));
  }

  @Transactional
  @Override
  public void regist(RegistUserVo vo) {

    this.doRegist(vo);
  }

  protected List<DefaultSysUserDto> doQuery(QuerySysUserVo vo) {

    return getBaseMapper().query(vo);
  }

  protected DefaultSysUserDto doGetById(String id) {

    return getBaseMapper().findById(id);
  }

  protected void doBatchEnable(List<String> ids) {

    Wrapper<DefaultSysUser> updateWrapper = Wrappers.lambdaUpdate(DefaultSysUser.class)
        .set(DefaultSysUser::getAvailable, Boolean.TRUE).in(DefaultSysUser::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected void doBatchUnable(List<String> ids) {

    Wrapper<DefaultSysUser> updateWrapper = Wrappers.lambdaUpdate(DefaultSysUser.class)
        .set(DefaultSysUser::getAvailable, Boolean.FALSE).in(DefaultSysUser::getId, ids);
    getBaseMapper().update(updateWrapper);
  }

  protected DefaultSysUser doCreate(CreateSysUserVo vo) {

    Wrapper<DefaultSysUser> checkCodeWrapper = Wrappers.lambdaQuery(DefaultSysUser.class)
        .eq(DefaultSysUser::getCode, vo.getCode());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<DefaultSysUser> checkUsernameWrapper = Wrappers.lambdaQuery(DefaultSysUser.class)
        .eq(DefaultSysUser::getUsername, vo.getUsername());
    if (getBaseMapper().selectCount(checkUsernameWrapper) > 0) {
      throw new DefaultClientException("用户名重复，请重新输入！");
    }

    DefaultSysUser record = new DefaultSysUser();
    record.setId(IdUtil.getId());
    record.setCode(vo.getCode());
    record.setName(vo.getName());
    record.setUsername(vo.getUsername());
    record.setPassword(encoderWrapper.getEncoder().encode(vo.getPassword()));
    if (!StringUtil.isBlank(vo.getEmail())) {
      record.setEmail(vo.getEmail());
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      record.setTelephone(vo.getTelephone());
    }

    record.setGender(EnumUtil.getByCode(Gender.class, vo.getGender()));
    record.setAvailable(Boolean.TRUE);
    record.setDescription(
        StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    getBaseMapper().insert(record);

    return record;
  }

  protected void doUpdate(UpdateSysUserVo vo) {

    Wrapper<DefaultSysUser> checkCodeWrapper = Wrappers.lambdaQuery(DefaultSysUser.class)
        .eq(DefaultSysUser::getCode, vo.getCode()).ne(DefaultSysUser::getId, vo.getId());
    if (getBaseMapper().selectCount(checkCodeWrapper) > 0) {
      throw new DefaultClientException("编号重复，请重新输入！");
    }

    Wrapper<DefaultSysUser> checkUsernameWrapper = Wrappers.lambdaQuery(DefaultSysUser.class)
        .eq(DefaultSysUser::getUsername, vo.getUsername()).ne(DefaultSysUser::getId, vo.getId());
    if (getBaseMapper().selectCount(checkUsernameWrapper) > 0) {
      throw new DefaultClientException("用户名重复，请重新输入！");
    }

    LambdaUpdateWrapper<DefaultSysUser> updateWrapper = Wrappers.lambdaUpdate(DefaultSysUser.class)
        .eq(DefaultSysUser::getId, vo.getId()).set(DefaultSysUser::getCode, vo.getCode())
        .set(DefaultSysUser::getUsername, vo.getUsername())
        .set(DefaultSysUser::getName, vo.getName())
        .set(DefaultSysUser::getEmail, null).set(DefaultSysUser::getTelephone, null)
        .set(DefaultSysUser::getGender, EnumUtil.getByCode(Gender.class, vo.getGender()))
        .set(DefaultSysUser::getAvailable, vo.getAvailable()).set(DefaultSysUser::getDescription,
            StringUtil.isBlank(vo.getDescription()) ? StringPool.EMPTY_STR : vo.getDescription());

    if (!StringUtil.isBlank(vo.getPassword())) {
      updateWrapper.set(DefaultSysUser::getPassword,
          encoderWrapper.getEncoder().encode(vo.getPassword()));
    }

    if (!StringUtil.isBlank(vo.getEmail())) {
      updateWrapper.set(DefaultSysUser::getEmail, vo.getEmail());
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      updateWrapper.set(DefaultSysUser::getTelephone, vo.getTelephone());
    }

    getBaseMapper().update(updateWrapper);
  }

  protected List<DefaultSysUserDto> doSelector(SysUserSelectorVo vo) {

    return getBaseMapper().selector(vo);
  }

  protected void doRegist(RegistUserVo vo) {

    Wrapper<DefaultSysUser> queryWrapper = Wrappers.lambdaQuery(DefaultSysUser.class)
        .eq(DefaultSysUser::getUsername, vo.getUsername());
    if (getBaseMapper().selectCount(queryWrapper) > 0) {
      throw new DefaultClientException("用户名重复，请重新输入！");
    }

    DefaultSysUser record = new DefaultSysUser();
    record.setId(IdUtil.getId());
    record.setCode(generateCodeService.generate(new UserCodeType()));
    record.setName(vo.getName());
    record.setUsername(vo.getUsername());
    record.setPassword(encoderWrapper.getEncoder().encode(vo.getPassword()));
    if (!StringUtil.isBlank(vo.getEmail())) {
      record.setEmail(vo.getEmail());
    }

    if (!StringUtil.isBlank(vo.getTelephone())) {
      record.setTelephone(vo.getTelephone());
    }

    record.setGender(Gender.UNKNOWN);
    record.setAvailable(Boolean.TRUE);
    record.setDescription(StringPool.EMPTY_STR);

    getBaseMapper().insert(record);
  }

  @CacheEvict(value = {DefaultSysUserDto.CACHE_NAME, UserDto.CACHE_NAME,
      UserInfoDto.CACHE_NAME}, key = "#key")
  @Override
  public void cleanCacheByKey(String key) {

  }

  @Override
  public void onApplicationEvent(UpdateUserEvent event) {

    ISysUserService thisService = getThis(this.getClass());
    thisService.cleanCacheByKey(event.getId());
  }

  public static class UserCodeType implements GenerateCodeType {

  }

  @Component
  public static class UserCodeGenerator extends AbstractFlowGenerator {

    @Override
    public GenerateCodeType getType() {

      return new UserCodeType();
    }

    @Override
    protected int getCodeLength() {

      return 5;
    }

    @Override
    protected String getPreffix() {

      return "R";
    }
  }
}
