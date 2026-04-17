package com.lframework.starter.bpm.handlers;

import com.lframework.starter.bpm.entity.FlowCuInstance;
import com.lframework.starter.bpm.service.FlowCuInstanceService;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.utils.RequestUtil;
import java.util.List;
import java.util.Map;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.dto.PromptContent;
import org.dromara.warm.flow.ui.service.ChartExtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BpmChartExtService implements ChartExtService {

  @Autowired
  private FlowCuInstanceService flowCuInstanceService;

  @Override
  public void execute(DefJson defJson) {
    String uri = RequestUtil.getRequest().getRequestURI();
    String instanceId = uri.substring(uri.lastIndexOf("/") + 1);
    FlowCuInstance cuInstance = flowCuInstanceService.getById(instanceId);
    defJson.setTopText(cuInstance.getTitle());

    defJson.getNodeList().forEach(nodeJson -> {
      Map<String, Object> extMap = nodeJson.getExtMap();
      if (CollectionUtil.isNotEmpty(extMap) && extMap.containsKey("handleInfos")) {
        Object handleInfosValue = extMap.get("handleInfos");
        if (!(handleInfosValue instanceof List<?> handleInfos)) {
          return;
        }

        for (int i = 0; i < handleInfos.size(); i++) {
          Object handleInfoValue = handleInfos.get(i);
          if (!(handleInfoValue instanceof Map<?, ?> handleInfo)) {
            continue;
          }

          PromptContent.InfoItem item = new PromptContent.InfoItem();
          item.setPrefix("处理人" + (handleInfos.size() > 1 ? (i + 1) : "") + ": ");
          item.setContent(stringValue(handleInfo.get("createBy")));
          nodeJson.getPromptContent().getInfo().add(item);

          item = new PromptContent.InfoItem();
          item.setPrefix("处理方式: ");
          item.setContent(stringValue(handleInfo.get("skipType")));
          nodeJson.getPromptContent().getInfo().add(item);

          item = new PromptContent.InfoItem();
          item.setPrefix("处理时间: ");
          item.setContent(stringValue(handleInfo.get("createTime")));
          nodeJson.getPromptContent().getInfo().add(item);

          if (handleInfo.containsKey("message")) {
            item = new PromptContent.InfoItem();
            item.setPrefix("说明: ");
            item.setContent(stringValue(handleInfo.get("message")));
            nodeJson.getPromptContent().getInfo().add(item);
          }
        }
      }
    });
  }

  private String stringValue(Object value) {
    return value == null ? null : value.toString();
  }
}
