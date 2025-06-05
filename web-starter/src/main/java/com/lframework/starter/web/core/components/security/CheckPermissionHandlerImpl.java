package com.lframework.starter.web.core.components.security;

import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
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
  public boolean valid(PermissionCalcType calcType, String... permissions) {
    if (ArrayUtil.isEmpty(permissions)) {
      return false;
    }

    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    if (user == null) {
      return false;
    }

    if (user.hasAdminPermission()) {
      if (log.isDebugEnabled()) {
        log.debug("当前用户是管理员，通过权限校验");
      }
      return true;
    }
    Set<String> permissionSet = user.getPermissions();

    boolean valid = CollectionUtil.isNotEmpty(permissionSet)
        && (calcType == PermissionCalcType.OR ?
        Arrays.stream(permissions).anyMatch(
            pattern -> permissionSet.stream().anyMatch(item -> StringUtil.strMatch(pattern, item)))
        : Arrays.stream(permissions).allMatch(
            pattern -> permissionSet.stream()
                .anyMatch(item -> StringUtil.strMatch(pattern, item))));

    if (log.isDebugEnabled()) {
      log.debug("当前用户权限={}, 需要权限={}, 是否通过权限校验={}", permissionSet, permissions,
          valid);
    }

    return valid;
  }
}
