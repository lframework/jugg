package com.lframework.starter.mybatis.service.message;

import com.lframework.starter.mybatis.dto.message.TodoTaskDto;
import com.lframework.starter.mybatis.resp.PageResult;

public interface ITodoTaskService {

  /**
   * 查询待办事项
   *
   * @return
   */
  PageResult<TodoTaskDto> queryTodoTasks();
}
