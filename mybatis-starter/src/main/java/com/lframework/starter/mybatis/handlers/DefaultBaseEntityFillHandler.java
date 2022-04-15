package com.lframework.starter.mybatis.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.constants.MyBatisStringPool;
import com.lframework.web.common.security.AbstractUserDetails;
import com.lframework.web.common.security.SecurityUtil;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Value;

/**
 * BaseEntity自动填充处理器
 *
 * @author zmj
 */
@Slf4j
public class DefaultBaseEntityFillHandler implements MetaObjectHandler {

  @Value("${default-setting.default-user-id:'1'}")
  private String defaultUserId;

  @Override
  public void insertFill(MetaObject metaObject) {

    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    if (ObjectUtil.isNotNull(user)) {
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_BY, String.class,
          user.getId());
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          user.getId());
    } else {
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_BY, String.class,
          defaultUserId);
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          defaultUserId);
    }

    this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_TIME, LocalDateTime.class,
        LocalDateTime.now());
    this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_TIME, LocalDateTime.class,
        LocalDateTime.now());
  }

  @Override
  public void updateFill(MetaObject metaObject) {

    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    if (ObjectUtil.isNotNull(user)) {
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          user.getId());
    } else {
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          defaultUserId);
    }

    this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_TIME, LocalDateTime.class,
        LocalDateTime.now());
  }
}
