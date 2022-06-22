package com.lframework.starter.mybatis.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lframework.starter.mybatis.service.BaseMpService;

public abstract class BaseMpServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T>
    implements BaseMpService<T> {

  @Override
  public T getOne(Wrapper<T> queryWrapper) {
    // 重写，默认不抛异常
    return super.getOne(queryWrapper, false);
  }
}
