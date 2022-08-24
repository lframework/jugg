package com.lframework.starter.security.controller.message;

import com.lframework.common.utils.ThreadUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.message.IMessageBusService;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.ApplicationUtil;
import com.lframework.web.common.security.SecurityUtil;
import com.lframework.web.common.threads.DefaultCallable;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 消息总线Controller 由于考虑到部署的难易性，暂不使用websocket，而是使用定时器轮询的方式 故此会有此Controller
 * 后续如果改用websocket，那么就废弃此Controller
 */
@Api(tags = "消息总线")
@Slf4j
@Validated
@RestController
@RequestMapping("/message/bus")
public class MessageBusController extends DefaultBaseController {

  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<Map<String, PageResult>> queryTodoTasks() {

    // getBeans key是BeanName
    Map<String, IMessageBusService> oriBeans = ApplicationUtil.getBeansOfType(
        IMessageBusService.class);
    // 重新根据type分组
    // 根据type去重
    List<IMessageBusService> beans = oriBeans.values().stream()
        .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(
            Comparator.comparing(IMessageBusService::getType))), ArrayList::new));

    Map<String, Future<PageResult>> futureMap = new HashMap<>(beans.size(), 1);

    for (IMessageBusService bean : beans) {
      Future<PageResult> future = ThreadUtil.execAsync(
          new DefaultCallable<>(SecurityUtil.getCurrentUser(), () -> bean.queryList()));
      futureMap.put(bean.getType(), future);
    }

    Map<String, PageResult> results = futureMap.entrySet().stream()
        .collect(Collectors.toMap((Entry::getKey), (t -> {
          try {
            return t.getValue().get();
          } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
          }
          return new PageResult<>();
        })));

    return InvokeResultBuilder.success(results);
  }
}
