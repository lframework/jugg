package com.lframework.starter.security.impl.message;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.security.bo.message.TodoTaskBo;
import com.lframework.starter.security.service.message.ITodoTaskService;

public class TodoTaskServiceImpl implements ITodoTaskService {

  @Override
  public PageResult<TodoTaskBo> queryBpmTodoTasks() {
    return new PageResult<>();
  }
}
