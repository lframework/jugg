package com.lframework.starter.web.components.generator.impl;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.common.locker.LockBuilder;
import com.lframework.starter.common.locker.Locker;
import com.lframework.starter.common.utils.DateUtil;
import com.lframework.starter.web.common.tenant.TenantContextHolder;
import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import com.lframework.starter.web.components.redis.RedisHandler;
import com.lframework.starter.web.utils.TenantUtil;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 流水号生成器
 *
 * @author zmj
 */
public abstract class AbstractFlowGenerator extends AbstractGenerator implements Generator {

  private static final String LOCK_KEY = "flow_generator_index";

  @Autowired
  private RedisHandler redisHandler;

  @Autowired
  private LockBuilder lockBuilder;

  @Override
  public String generate() {

    GenerateCodeType type = getType();
    if (type == null) {
      throw new DefaultSysException("code为null！");
    }
    String lockerName = LOCK_KEY + type.getClass().getName();
    String nowStr = DateUtil.formatDate(LocalDate.now(), "yyyyMMdd");
    String redisKey =
        nowStr + "_" + (TenantUtil.enableTenant() ? TenantContextHolder.getTenantId() : "noTenant")
            + "_" + lockerName;
    Locker locker = lockBuilder.buildLocker(redisKey + "_Locker", 60000L, 5000L);
    long no;

    if (locker.lock()) {
      try {
        no = redisHandler.incr(redisKey, 1L);
      } finally {
        locker.unLock();
      }
    } else {
      throw new DefaultClientException("生成单号失败，请稍后重试！");
    }
    redisHandler.expire(redisKey, 86400000L);

    String noStr = String.valueOf(no);
    if (noStr.length() > getCodeLength()) {
      throw new DefaultSysException("单号超长！");
    }

    StringBuilder builder = new StringBuilder();
    builder.append(getPreffix()).append(nowStr);
    for (int i = 0, len = getCodeLength() - noStr.length(); i < len; i++) {
      builder.append("0");
    }

    return builder.append(noStr).toString();
  }

  /**
   * 获取流水号长度
   *
   * @return
   */
  protected int getCodeLength() {

    return 10;
  }

  protected String getPreffix() {

    return "";
  }
}
