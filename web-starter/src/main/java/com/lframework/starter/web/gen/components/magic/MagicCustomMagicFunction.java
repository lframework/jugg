package com.lframework.starter.web.gen.components.magic;

import com.lframework.starter.web.core.components.security.AbstractUserDetails;
import com.lframework.starter.web.core.components.security.SecurityUtil;
import com.lframework.starter.web.core.components.tenant.TenantContextHolder;
import org.springframework.stereotype.Component;
import org.ssssssss.magicapi.core.config.MagicFunction;
import org.ssssssss.script.annotation.Comment;
import org.ssssssss.script.annotation.Function;

@Component
public class MagicCustomMagicFunction implements MagicFunction {

  @Function
  @Comment("获取登录人ID")
  public static AbstractUserDetails current_user() {
    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    return user;
  }

  @Function
  @Comment("获取租户ID")
  public static String current_tenant_id() {
    return TenantContextHolder.getTenantId() == null ? null
        : TenantContextHolder.getTenantId().toString();
  }
}
