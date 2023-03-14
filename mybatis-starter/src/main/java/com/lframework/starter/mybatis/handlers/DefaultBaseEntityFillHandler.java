package com.lframework.starter.mybatis.handlers;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.lframework.starter.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.constants.MyBatisStringPool;
import com.lframework.starter.web.common.security.AbstractUserDetails;
import com.lframework.starter.web.common.security.SecurityUtil;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.function.Supplier;
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

  @Value("${default-setting.default-user-name:'系统管理员'}")
  private String defaultUserName;

  @Override
  public void insertFill(MetaObject metaObject) {

    AbstractUserDetails user = SecurityUtil.getCurrentUser();
    if (ObjectUtil.isNotNull(user)) {
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_BY, String.class,
          user.getName());
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          user.getName());
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_BY_ID, String.class,
          user.getId());
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY_ID, String.class,
          user.getId());
    } else {
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_BY, String.class,
          defaultUserName);
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          defaultUserName);
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_CREATE_BY_ID, String.class,
          defaultUserId);
      this.strictInsertFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY_ID, String.class,
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
      this.strictUpdateFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          user.getName());
      this.strictUpdateFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY_ID, String.class,
          user.getId());
    } else {
      this.strictUpdateFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY, String.class,
          defaultUserName);
      this.strictUpdateFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_BY_ID, String.class,
          defaultUserId);
    }

    this.strictUpdateFill(metaObject, MyBatisStringPool.COLUMN_UPDATE_TIME, LocalDateTime.class,
        LocalDateTime.now());
  }

  @Override
  public MetaObjectHandler strictFillStrategy(MetaObject metaObject, String fieldName,
      Supplier<?> fieldVal) {
    if (MyBatisStringPool.COLUMN_UPDATE_BY.equals(fieldName)
        || MyBatisStringPool.COLUMN_UPDATE_BY_ID.equals(fieldName)
        || MyBatisStringPool.COLUMN_UPDATE_TIME.equals(fieldName)) {
      Object obj = fieldVal.get();
      if (Objects.nonNull(obj)) {
        metaObject.setValue(fieldName, obj);
      }
    } else {
      return MetaObjectHandler.super.strictFillStrategy(metaObject, fieldName, fieldVal);
    }

    return this;
  }
}
