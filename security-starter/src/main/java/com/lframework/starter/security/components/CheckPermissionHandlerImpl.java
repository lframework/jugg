package com.lframework.starter.security.components;

import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.web.components.security.AbstractUserDetails;
import com.lframework.starter.web.utils.SecurityUtil;
import java.util.Arrays;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

/**
 * 权限验证实现
 *
 * @author zmj
 */
@Slf4j
public class CheckPermissionHandlerImpl implements CheckPermissionHandler {

  @Override
  public boolean valid(String... permissions) {

    if (ArrayUtil.isEmpty(permissions)) {
      return true;
    }

    AbstractUserDetails user = SecurityUtil.getCurrentUser();

    if (user.isAdmin()) {
      if (log.isDebugEnabled()) {
        log.debug("当前用户是管理员，通过权限校验");
      }
      return true;
    }
    Set<String> permissionSet = user.getPermissions();

    boolean valid = CollectionUtil.isNotEmpty(permissionSet) && Arrays.stream(permissions)
        .anyMatch(permissionSet::contains);

    if (log.isDebugEnabled()) {
      log.debug("当前用户权限={}, 需要权限={}, 是否通过权限校验={}", permissionSet, permissions, valid);
    }

    return valid;
  }
}
