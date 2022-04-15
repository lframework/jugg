package com.lframework.starter.bpm.impl;

import com.lframework.common.constants.StringPool;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.dto.message.TodoTaskDto;
import com.lframework.starter.web.components.security.IUserTokenResolver;
import com.lframework.starter.mybatis.service.message.ITodoTaskService;
import com.lframework.starter.web.utils.HttpUtil;
import com.lframework.starter.web.utils.JsonUtil;
import com.lframework.starter.web.utils.RequestUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BpmTodoTaskServiceImpl implements ITodoTaskService {

  @Value("${bpm.api-url:''}")
  private String apiUrl;

  @Value("${bpm.jump-url:''}")
  private String jumpUrl;

  @Autowired
  private IUserTokenResolver userTokenResolver;

  @Override
  public PageResult<TodoTaskDto> queryTodoTasks() {
    Map<String, Object> reqParams = new HashMap<>();
    reqParams.put("limit", 10);
    reqParams.put("sort", "DESC");
    try {
      String retStr = HttpUtil.doPost(apiUrl + "/bpm/my/todoTaskList", reqParams,
          RequestUtil.getHeaders(), 5000);
      if (JsonUtil.isJson(retStr)) {
        Map retMap = JsonUtil.parseObject(retStr, Map.class);
        if (retMap.get("isOk") != null && Boolean.valueOf(retMap.get("isOk").toString())) {
          // 获取数据成功
          List<Map<String, Object>> rowMap = (List<Map<String, Object>>) retMap.get("rows");
          List<TodoTaskDto> results = rowMap.stream().map(t -> {
            TodoTaskDto result = new TodoTaskDto();
            result.setTitle(t.get("subject").toString());
            result.setCreateTime(t.get("createTime").toString());
            result.setJumpUrl(
                jumpUrl + "/agilebpm-ui/index.html?tokenKey=" + userTokenResolver.getTokenKey()
                    + "&token=" + userTokenResolver.getFullToken());

            return result;
          }).collect(Collectors.toList());

          // 模拟分页数据
          PageResult<TodoTaskDto> pageResult = new PageResult<>();
          pageResult.setTotalCount(Integer.parseInt(retMap.get("total").toString()));
          pageResult.setDatas(results);

          return pageResult;
        }
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return new PageResult<>();
  }
}
