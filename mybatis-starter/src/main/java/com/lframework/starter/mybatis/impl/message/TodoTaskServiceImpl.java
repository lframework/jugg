package com.lframework.starter.mybatis.impl.message;

import com.lframework.starter.mybatis.dto.message.TodoTaskDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.message.ITodoTaskService;

public class TodoTaskServiceImpl implements ITodoTaskService {

  @Override
  public PageResult<TodoTaskDto> queryTodoTasks() {

    return new PageResult<>();
  }
}
