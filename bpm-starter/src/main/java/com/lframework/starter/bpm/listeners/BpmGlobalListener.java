package com.lframework.starter.bpm.listeners;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.dto.FlowInstanceExtDto;
import com.lframework.starter.bpm.entity.FlowCuApproveHis;
import com.lframework.starter.bpm.entity.FlowCuInstance;
import com.lframework.starter.bpm.enums.FlowInstanceStatus;
import com.lframework.starter.bpm.enums.FlowSkipType;
import com.lframework.starter.bpm.service.FlowCuApproveHisService;
import com.lframework.starter.bpm.service.FlowCuInstanceService;
import com.lframework.starter.bpm.transfers.FlowCuInstanceTransfer;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mq.core.service.MqProducerService;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.core.utils.JsonUtil;
import com.lframework.starter.web.inner.dto.message.SysSiteMessageDto;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.system.SysUserService;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.dromara.warm.flow.core.FlowEngine;
import org.dromara.warm.flow.core.dto.DefJson;
import org.dromara.warm.flow.core.dto.FlowCombine;
import org.dromara.warm.flow.core.dto.FlowParams;
import org.dromara.warm.flow.core.dto.NodeJson;
import org.dromara.warm.flow.core.dto.PathWayData;
import org.dromara.warm.flow.core.entity.Node;
import org.dromara.warm.flow.core.enums.CooperateType;
import org.dromara.warm.flow.core.handler.PermissionHandler;
import org.dromara.warm.flow.core.listener.GlobalListener;
import org.dromara.warm.flow.core.listener.ListenerVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BpmGlobalListener implements GlobalListener {

  private static final ThreadLocal<ListenerVariable> LISTENER_VARIABLE = new ThreadLocal<>();

  @Autowired
  private FlowCuInstanceService flowCuInstanceService;

  @Autowired
  private SysUserService sysUserService;

  @Autowired
  private FlowCuApproveHisService flowCuApproveHisService;

  @Autowired
  private PermissionHandler permissionHandler;

  @Autowired
  private MqProducerService mqProducerService;

  /**
   * 开始监听器，任务开始办理时执行
   *
   * @param listenerVariable 监听器变量
   */
  public void start(ListenerVariable listenerVariable) {
    log.debug("全局开始监听器开始执行......");
    log.debug(JsonUtil.toJsonPrettyStr(listenerVariable));

    LISTENER_VARIABLE.set(listenerVariable);

    if (FlowInstanceStatus.REFUSE.getCode()
        .equals(listenerVariable.getFlowParams().getFlowStatus())) {
      this.addHandleInfo(listenerVariable, FlowSkipType.APPROVE_REFUSE);
    } else if (FlowInstanceStatus.UNDO.getCode()
        .equals(listenerVariable.getFlowParams().getFlowStatus())) {
      this.addHandleInfo(listenerVariable, FlowSkipType.UNDO);
    } else if (FlowInstanceStatus.REVOKE.getCode()
        .equals(listenerVariable.getFlowParams().getFlowStatus())) {
      this.addHandleInfo(listenerVariable, FlowSkipType.REJECT);
    } else if (FlowInstanceStatus.TERMINATION.getCode()
        .equals(listenerVariable.getFlowParams().getFlowStatus())) {
      this.addHandleInfo(listenerVariable, FlowSkipType.TERMINATION);
    } else if ("PASS"
        .equals(listenerVariable.getFlowParams().getSkipType())) {
      if (!"start".equals(listenerVariable.getNode().getNodeCode())) {
        this.addHandleInfo(listenerVariable, FlowSkipType.APPROVE_PASS);
      }
    } else if (CooperateType.isVoteSign(listenerVariable.getNode().getNodeRatio())
        && "REJECT".equals(listenerVariable.getFlowParams().getSkipType())) {
      this.addHandleInfo(listenerVariable, FlowSkipType.REJECT);
    }

    FlowInstanceExtDto ext = parseExt(listenerVariable);
    if (ext == null) {
      log.info("扩展字段为空，不监听");
      return;
    }
    List<BpmBizListener> bizListeners = parseBizListeners(ext);

    for (BpmBizListener bizListener : bizListeners) {
      bizListener.start(ext, listenerVariable);
    }

    log.debug("全局开始监听器执行结束......");
  }

  /**
   * 分派监听器，动态修改代办任务信息
   *
   * @param listenerVariable 监听器变量
   */
  public void assignment(ListenerVariable listenerVariable) {
    log.debug("全局分派监听器开始执行......");
    log.debug(JsonUtil.toJsonPrettyStr(listenerVariable));

    if ("start".equals(listenerVariable.getNode().getNodeCode())) {
      this.addHandleInfo(listenerVariable, FlowSkipType.START);
    }

    FlowInstanceExtDto ext = parseExt(listenerVariable);
    if (ext == null) {
      log.info("扩展字段为空，不监听");
      return;
    }
    List<BpmBizListener> bizListeners = parseBizListeners(ext);

    for (BpmBizListener bizListener : bizListeners) {
      bizListener.assignment(ext, listenerVariable);
    }

    log.debug("全局分派监听器执行结束......");
  }

  /**
   * 完成监听器，当前任务完成后执行
   *
   * @param listenerVariable 监听器变量
   */
  public void finish(ListenerVariable listenerVariable) {
    log.debug("全局完成监听器开始执行......");
    log.debug(JsonUtil.toJsonPrettyStr(listenerVariable));

    ListenerVariable beforeListenerVariable = LISTENER_VARIABLE.get();
    LISTENER_VARIABLE.remove();

    LocalDateTime now = LocalDateTime.now();
    if (beforeListenerVariable.getInstance() == null) {
      // 这里表示流程实例刚创建完成
      String title = FlowCuInstanceTransfer.getTitle();
      FlowCuInstanceTransfer.clearTitle();
      if (StringUtil.isBlank(title)) {
        // 设置默认标题
        SysUser createBy = sysUserService.findById(listenerVariable.getInstance().getCreateBy());
        title = StringUtil.format("{}在{}发起的{}", createBy.getName(),
            DateUtil.formatDateTime(now, "yyyy年MM月dd日HH时mm分ss秒"),
            listenerVariable.getDefinition().getFlowName());
      }
      FlowCuInstance flowCuInstance = new FlowCuInstance();
      flowCuInstance.setId(listenerVariable.getInstance().getId());
      flowCuInstance.setTitle(title);
      flowCuInstance.setStartTime(
          DateUtil.toLocalDateTime(listenerVariable.getInstance().getCreateTime()));

      FlowInstanceExtDto ext = parseExt(listenerVariable);
      if (ext != null) {
        flowCuInstance.setBizType(ext.getBizType());
        flowCuInstance.setBizFlag(ext.getBizFlag());
      }
      flowCuInstanceService.save(flowCuInstance);
    }

    FlowParams flowParams = beforeListenerVariable.getFlowParams();
    if ("PASS".equals(flowParams.getSkipType()) && FlowInstanceStatus.APPROVING.getCode()
        .equals(listenerVariable.getInstance().getFlowStatus())) {
      FlowCombine flowCombine = FlowEngine.defService()
          .getFlowCombineNoDef(listenerVariable.getDefinition().getId());
      // 获取后续任务节点结合
      PathWayData pathWayData = new PathWayData().setInsId(listenerVariable.getInstance().getId())
          .setSkipType(flowParams.getSkipType());
      Node nextNode = FlowEngine.nodeService()
          .getNextNode(listenerVariable.getNode(), flowParams.getNodeCode()
              , flowParams.getSkipType(), pathWayData, flowCombine);
      List<Node> nextNodes = FlowEngine.nodeService().getNextByCheckGateway(flowParams.getVariable()
          , nextNode, pathWayData, flowCombine);

      if (CollectionUtil.isNotEmpty(nextNodes)) {
        for (Node node : nextNodes) {
          if (StringUtil.isBlank(node.getPermissionFlag())) {
            continue;
          }
          List<String> permissionFlagList = new ArrayList<>(
              Arrays.asList(node.getPermissionFlag().split("@@")));

          FlowCuInstance cuInstance = flowCuInstanceService.getById(
              listenerVariable.getInstance().getId());
          SysUser startBy = sysUserService.findById(listenerVariable.getInstance().getCreateBy());
          List<String> notifyUserIds = permissionHandler.convertPermissions(permissionFlagList);
          SysSiteMessageDto siteMessageDto = new SysSiteMessageDto();
          siteMessageDto.setUserIdList(
              notifyUserIds.stream().distinct().collect(Collectors.toList()));
          siteMessageDto.setTitle("【" + node.getNodeName() + "】新的待审核流程");
          siteMessageDto.setContent(
              StringUtil.format(
                  "您有待审核流程需要处理，请及时处理。流程标题：{}，发起人：{}，发起时间：{}。",
                  cuInstance.getTitle(), startBy.getName(), DateUtil.formatDateTime(
                      DateUtil.toLocalDateTime(listenerVariable.getInstance().getCreateTime()),
                      "yyyy年MM月dd日HH时mm分ss秒")));
          siteMessageDto.setBizKey(String.valueOf(listenerVariable.getInstance().getId()));
          siteMessageDto.setCreateUserId(SecurityUtil.getCurrentUser().getId());

          mqProducerService.createSysSiteMessage(siteMessageDto);
        }
      }
    }

    if ("end".equals(listenerVariable.getInstance().getNodeCode())) {
      this.endInstance(listenerVariable.getInstance().getId(), now);
    }

    FlowInstanceExtDto ext = parseExt(listenerVariable);
    if (ext == null) {
      log.info("扩展字段为空，不监听");
      return;
    }
    List<BpmBizListener> bizListeners = parseBizListeners(ext);

    for (BpmBizListener bizListener : bizListeners) {
      bizListener.finish(ext, listenerVariable, beforeListenerVariable);
    }

    // 这里单独把完成流程通知一下
    if ("end".equals(listenerVariable.getInstance().getNodeCode())) {
      if (FlowInstanceStatus.FINISH.getCode()
          .equals(listenerVariable.getInstance().getFlowStatus())) {
        // 这里是完成了流程
        for (BpmBizListener bizListener : bizListeners) {
          // 这里不用获取当前审核人，因为当前登录人就是审核人
          bizListener.businessComplete(listenerVariable,
              listenerVariable.getInstance().getBusinessId(),
              listenerVariable.getInstance().getCreateBy());
        }
      }
    }

    log.debug("全局完成监听器执行结束......");
  }

  /**
   * 创建监听器，任务创建时执行
   *
   * @param listenerVariable 监听器变量
   */
  public void create(ListenerVariable listenerVariable) {
    log.debug("全局创建监听器开始执行......");
    log.debug(JsonUtil.toJsonPrettyStr(listenerVariable));

    FlowInstanceExtDto ext = parseExt(listenerVariable);
    if (ext == null) {
      log.info("扩展字段为空，不监听");
      return;
    }
    List<BpmBizListener> bizListeners = parseBizListeners(ext);

    for (BpmBizListener bizListener : bizListeners) {
      bizListener.create(ext, listenerVariable);
    }

    log.debug("全局创建监听器执行结束......");
  }

  private FlowInstanceExtDto parseExt(ListenerVariable listenerVariable) {
    if (listenerVariable == null) {
      return null;
    }
    if (listenerVariable.getInstance() == null) {
      return null;
    }
    if (StringUtil.isBlank(listenerVariable.getInstance().getExt())) {
      return null;
    }
    return JsonUtil.parseObject(listenerVariable.getInstance().getExt(), FlowInstanceExtDto.class);
  }

  private List<BpmBizListener> parseBizListeners(FlowInstanceExtDto ext) {

    Map<String, BpmBizListener> bizListeners = ApplicationUtil.getBeansOfType(BpmBizListener.class);

    return bizListeners.values().stream().filter(bizListener -> bizListener.isMatch(ext)).collect(
        Collectors.toList());
  }

  private void addHandleInfo(ListenerVariable listenerVariable, FlowSkipType skipType) {

    LocalDateTime now = LocalDateTime.now();
    String defJsonStr = listenerVariable.getInstance().getDefJson();

    if (StringUtil.isNotBlank(defJsonStr)) {
      DefJson defJson = FlowEngine.jsonConvert.strToBean(defJsonStr, DefJson.class);
      for (NodeJson nodeJson : defJson.getNodeList()) {
        if (nodeJson.getNodeCode().equals(listenerVariable.getNode().getNodeCode())) {
          List<Map<String, Object>> handleInfos = (List<Map<String, Object>>) nodeJson.getExtMap()
              .get("handleInfos");
          if (handleInfos == null) {
            handleInfos = new ArrayList<>();
            nodeJson.getExtMap().put("handleInfos", new ArrayList<>());
          }
          String userId = listenerVariable.getFlowParams().getHandler();
          SysUser sysUser = sysUserService.findById(userId);

          FlowCuApproveHis approveHis = new FlowCuApproveHis();
          approveHis.setId(IdUtil.getIdLong());
          approveHis.setDefinitionId(listenerVariable.getDefinition().getId());
          approveHis.setInstanceId(listenerVariable.getInstance().getId());
          approveHis.setNodeCode(nodeJson.getNodeCode());
          approveHis.setNodeName(nodeJson.getNodeName());
          approveHis.setTaskId(
              listenerVariable.getTask() == null ? null : listenerVariable.getTask().getId());

          Map<String, Object> handleInfo = new LinkedHashMap<>();
          if (sysUser != null && StringUtil.isNotEmpty(sysUser.getName())) {
            approveHis.setCreateById(sysUser.getId());
            approveHis.setCreateBy(sysUser.getName());

            handleInfo.put("createById", sysUser.getId());
            handleInfo.put("createBy", sysUser.getName());

            if (listenerVariable.getFlowParams() != null && StringUtil.isNotBlank(
                listenerVariable.getFlowParams().getMessage())) {
              approveHis.setMessage(listenerVariable.getFlowParams().getMessage());

              handleInfo.put("message", approveHis.getMessage());
            }
          }
          approveHis.setSkipType(skipType);

          handleInfo.put("skipType", skipType.getDesc());

          approveHis.setCreateTime(now);
          handleInfo.put("createTime",
              DateUtil.formatDateTime(approveHis.getCreateTime(), "yyyy年MM月dd日HH时mm分ss秒"));

          flowCuApproveHisService.save(approveHis);

          handleInfos.add(handleInfo);

          nodeJson.getExtMap().put("handleInfos", handleInfos);
        }
      }
      listenerVariable.getInstance().setDefJson(FlowEngine.jsonConvert.objToStr(defJson));

      FlowEngine.insService().updateById(listenerVariable.getInstance());
    }

    if (skipType == FlowSkipType.APPROVE_REFUSE || skipType == FlowSkipType.UNDO
        || skipType == FlowSkipType.TERMINATION) {
      this.endInstance(listenerVariable.getInstance().getId(), now);
    }
  }

  private void endInstance(Long instanceId, LocalDateTime endTime) {
    // 流程结束，记录结束时间
    Wrapper<FlowCuInstance> updateWrapper = Wrappers.lambdaUpdate(FlowCuInstance.class)
        .set(FlowCuInstance::getEndTime, endTime)
        .eq(FlowCuInstance::getId, instanceId);
    flowCuInstanceService.update(updateWrapper);
  }
}
