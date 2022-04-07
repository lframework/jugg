package com.lframework.starter.security.service.message;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.security.bo.message.TodoTaskBo;

public interface ITodoTaskService {

  /**
   * 查询待办事项
   *
   * @return
   */
  PageResult<TodoTaskBo> queryBpmTodoTasks();
}
