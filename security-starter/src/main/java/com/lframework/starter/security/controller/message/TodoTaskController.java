package com.lframework.starter.security.controller.message;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.message.TodoTaskDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.message.ITodoTaskService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.bo.message.TodoTaskBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 待办任务Controller
 */
@Api(tags = "待办事项")
@Slf4j
@Validated
@RestController
@RequestMapping("/message/todo")
public class TodoTaskController extends DefaultBaseController {

  @Autowired
  private ITodoTaskService todoTaskService;

  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<TodoTaskBo>> queryTodoTasks() {

    PageResult<TodoTaskDto> pageResult = todoTaskService.queryTodoTasks();

    List<TodoTaskDto> datas = pageResult.getDatas();
    List<TodoTaskBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(TodoTaskBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
