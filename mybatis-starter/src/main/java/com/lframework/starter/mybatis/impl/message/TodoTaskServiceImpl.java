package com.lframework.starter.mybatis.impl.message;

import com.lframework.starter.mybatis.dto.message.TodoTaskDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.message.TodoTaskService;
import java.io.Serializable;

public class TodoTaskServiceImpl implements TodoTaskService {

  @Override
  public String getType() {
    return "todo";
  }

  @Override
  public PageResult<TodoTaskDto> queryList() {

    return new PageResult<>();
  }

  @Override
  public TodoTaskDto findById(Serializable id) {
    // 空实现
    return null;
  }
}
