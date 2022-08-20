package com.lframework.starter.mybatis.impl;

import cn.hutool.core.exceptions.UtilException;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ReflectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.components.qrtz.DynamicQrtzJob;
import com.lframework.starter.mybatis.dto.qrtz.QrtzDto;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.enums.qrtz.QrtzJobType;
import com.lframework.starter.mybatis.mappers.QrtzMapper;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IQrtzService;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.qrtz.CreateQrtzVo;
import com.lframework.starter.mybatis.vo.qrtz.QueryQrtzVo;
import com.lframework.starter.mybatis.vo.qrtz.UpdateQrtzVo;
import com.lframework.starter.web.components.qrtz.QrtzHandler;
import com.lframework.starter.web.utils.EnumUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Slf4j
@Service
public class QrtzServiceImpl implements IQrtzService {

  @Autowired
  private SchedulerFactoryBean schedulerFactoryBean;

  @Autowired
  private QrtzMapper qrtzMapper;

  @Override
  public PageResult<QrtzDto> query(Integer pageIndex, Integer pageSize, QueryQrtzVo vo) {

    Assert.greaterThanZero(pageIndex);
    Assert.greaterThanZero(pageSize);

    vo.setJobClasses(new ArrayList<>());
    vo.getJobClasses().add(DynamicQrtzJob.class.getName());
    PageHelperUtil.startPage(pageIndex, pageSize);

    List<QrtzDto> datas = qrtzMapper.query(vo);
    PageResult<QrtzDto> pageResult = PageResultUtil.convert(new PageInfo<>(datas));
    datas.stream().forEach(this::renderDto);

    return pageResult;
  }

  @Override
  public QrtzDto findById(String name, String group) {
    QrtzDto data = qrtzMapper.findById(name, group);
    if (data == null) {
      return null;
    }

    return renderDto(data);
  }

  @OpLog(type = OpLogType.OTHER, name = "新增定时任务，名称：{}, 组：{}", params = {"#vo.name", "#vo.group"})
  @Transactional
  @Override
  public void create(CreateQrtzVo vo) {
    JobDetail jobDetail = QrtzHandler.getJob(vo.getName(), vo.getGroup());
    if (jobDetail != null) {
      throw new DefaultClientException("名称、组不允许重复，请重新输入！");
    }
    QrtzJobType jobType = EnumUtil.getByCode(QrtzJobType.class, vo.getJobType());
    if (jobType == QrtzJobType.EXCUTE_CLASS) {
      if (StringUtil.isBlank(vo.getTargetClassName())) {
        throw new DefaultClientException("类名不能为空！");
      }
      if (StringUtil.isBlank(vo.getTargetMethodName())) {
        throw new DefaultClientException("方法名不能为空！");
      }

      if (!CollectionUtil.isEmpty(vo.getTargetParamTypes()) || !CollectionUtil.isEmpty(
          vo.getTargetParams())) {
        int typeSize = vo.getTargetParamTypes() == null ? 0 : vo.getTargetParamTypes().size();
        int paramSize = vo.getTargetParams() == null ? 0 : vo.getTargetParams().size();
        if (typeSize != paramSize) {
          throw new DefaultClientException("参数类型与参数数量不匹配！");
        }
      }
      try {
        Class clazz = Class.forName(vo.getTargetClassName());
        ReflectUtil.newInstance(clazz);
        Class[] paramTypes = null;
        if (!CollectionUtil.isEmpty(vo.getTargetParamTypes())) {
          paramTypes = new Class[vo.getTargetParamTypes().size()];
          for (int i = 0; i < vo.getTargetParamTypes().size(); i++) {
            String targetParamType = vo.getTargetParamTypes().get(i);
            try {
              paramTypes[i] = Class.forName(targetParamType);
            } catch (ClassNotFoundException e) {
              throw new DefaultClientException(
                  "参数类型：" + targetParamType + "不存在，需要输入类型全名称，如：java.lang.String，请检查！");
            }
          }
        }
        // 验证一下参数
        ReflectUtil.getMethod(clazz, vo.getTargetMethodName(), paramTypes);

        Map<String, Object> jobDataMap = new HashMap<>();
        jobDataMap.put("targetClassName", vo.getTargetClassName());
        jobDataMap.put("targetMethodName", vo.getTargetMethodName());
        jobDataMap.put("targetParamTypes", vo.getTargetParamTypes());
        jobDataMap.put("targetParams", vo.getTargetParams());
        jobDataMap.put("jobType", vo.getJobType());

        QrtzHandler.addJob(vo.getName(), vo.getGroup(), DynamicQrtzJob.class,
            "custom_trigger_" + vo.getName(), vo.getGroup(), vo.getCron(), jobDataMap,
            vo.getDescription());
      } catch (ClassNotFoundException e) {
        throw new DefaultClientException("类：" + vo.getTargetClassName() + "不存在，请检查！");
      } catch (UtilException e) {
        throw new DefaultClientException("类：" + vo.getTargetClassName() + "无法加载，请检查！");
      }
    } else {
      Map<String, Object> jobDataMap = new HashMap<>();
      jobDataMap.put("script", vo.getScript());
      jobDataMap.put("jobType", vo.getJobType());

      QrtzHandler.addJob(vo.getName(), vo.getGroup(), DynamicQrtzJob.class,
          "custom_trigger_" + vo.getName(), vo.getGroup(), vo.getCron(), jobDataMap,
          vo.getDescription());
    }

    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "修改定时任务，名称：{}, 组：{}", params = {"#vo.oriName",
      "#vo.oriGroup"})
  @Transactional
  @Override
  public void update(UpdateQrtzVo vo) {
    JobDetail jobDetail = QrtzHandler.getJob(vo.getOriName(), vo.getOriGroup());
    if (jobDetail == null) {
      throw new DefaultClientException("任务不存在！");
    }

    this.delete(vo.getOriName(), vo.getOriGroup());

    this.create(vo);

    OpLogUtil.setExtra(vo);
  }

  @OpLog(type = OpLogType.OTHER, name = "恢复定时任务，名称：{}, 组：{}", params = {"#name", "#group"})
  @Transactional
  @Override
  public void resume(String name, String group) {
    QrtzHandler.resume(name, group);
  }

  @OpLog(type = OpLogType.OTHER, name = "暂停定时任务，名称：{}, 组：{}", params = {"#name", "#group"})
  @Transactional
  @Override
  public void pause(String name, String group) {
    QrtzHandler.pause(name, group);
  }

  @OpLog(type = OpLogType.OTHER, name = "触发定时任务，名称：{}, 组：{}", params = {"#name", "#group"})
  @Transactional
  @Override
  public void trigger(String name, String group) {
    QrtzHandler.trigger(name, group);
  }

  @OpLog(type = OpLogType.OTHER, name = "删除定时任务，名称：{}, 组：{}", params = {"#name", "#group"})
  @Transactional
  @Override
  public void delete(String name, String group) {

    QrtzHandler.deleteJob(name, group, "custom_trigger_" + name, group);
  }

  private QrtzDto renderDto(QrtzDto data) {
    JobDetail jobDetail = QrtzHandler.getJob(data.getName(), data.getGroup());
    Integer jobType = jobDetail.getJobDataMap().getIntValue("jobType");
    data.setJobType(EnumUtil.getByCode(QrtzJobType.class, jobType));
    data.setTargetClassName(jobDetail.getJobDataMap().getString("targetClassName"));
    data.setTargetMethodName(jobDetail.getJobDataMap().getString("targetMethodName"));
    data.setTargetParamTypes((List<String>) jobDetail.getJobDataMap().get("targetParamTypes"));
    data.setTargetParams((List<String>) jobDetail.getJobDataMap().get("targetParams"));
    data.setScript(jobDetail.getJobDataMap().getString("script"));

    return data;
  }
}
