package com.lframework.starter.security.controller.message;

import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.service.message.ITodoTaskService;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办任务Controller
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/message/todo")
public class TodoTaskController extends DefaultBaseController {

  @Autowired
  private ITodoTaskService todoTaskService;

  @GetMapping("/query")
  public InvokeResult queryTodoTasks() {

    return InvokeResultBuilder.success(todoTaskService.queryBpmTodoTasks());
  }
}
