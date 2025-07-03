package com.lframework.starter.web.config;

import com.lframework.starter.web.core.aop.ControllerAspector;
import com.lframework.starter.web.core.aop.OpLogAspector;
import com.lframework.starter.web.core.aop.OpenApiAspect;
import com.lframework.starter.web.core.aop.OrderTimeLineLogAspect;
import com.lframework.starter.web.core.aop.PermissionAspect;
import com.lframework.starter.web.core.components.cache.CacheVariables;
import com.lframework.starter.web.core.components.generator.handler.impl.CurrentDateTimeRuleGenerateCodeHandler;
import com.lframework.starter.web.core.components.generator.handler.impl.CustomRandomStrGenerateCodeRuleHandler;
import com.lframework.starter.web.core.components.generator.handler.impl.FlowGenerateCodeRuleHandler;
import com.lframework.starter.web.core.components.generator.handler.impl.SnowFlakeGenerateCodeRuleHandler;
import com.lframework.starter.web.core.components.generator.handler.impl.StaticStrGenerateCodeRuleHandler;
import com.lframework.starter.web.core.components.generator.handler.impl.UUIDGenerateCodeRuleHandler;
import com.lframework.starter.web.core.components.resp.InvokeResultErrorBuilderWrapper;
import com.lframework.starter.web.core.components.resp.ResponseErrorBuilder;
import com.lframework.starter.web.core.components.security.UserTokenResolverImpl;
import com.lframework.starter.web.core.components.sign.CheckSignFactory;
import com.lframework.starter.web.core.components.sign.DefaultCheckSignFactory;
import com.lframework.starter.web.core.components.sign.handler.DefaultCheckSignHandler;
import com.lframework.starter.web.core.components.upload.handler.SecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.UploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.CosSecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.CosUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.LocalSecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.LocalUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.ObsSecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.ObsUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.OssSecurityUploadHandler;
import com.lframework.starter.web.core.components.upload.handler.impl.OssUploadHandler;
import com.lframework.starter.web.core.handlers.exception.WebExceptionHandler;
import com.lframework.starter.web.inner.components.oplog.AuthOpLogType;
import com.lframework.starter.web.inner.components.oplog.OtherOpLogType;
import com.lframework.starter.web.inner.components.oplog.SystemOpLogType;
import com.lframework.starter.web.inner.components.permission.OrderDataPermissionDataPermissionType;
import com.lframework.starter.web.inner.components.permission.ProductDataPermissionDataPermissionType;
import com.lframework.starter.web.inner.components.timeline.ApprovePassOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.ApproveReturnOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.CancelApproveOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.CreateOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.NormalOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.SendOrderTimeLineBizType;
import com.lframework.starter.web.inner.components.timeline.UpdateOrderTimeLineBizType;
import com.lframework.starter.web.inner.controller.AuthController;
import com.lframework.starter.web.inner.controller.QrtzController;
import com.lframework.starter.web.inner.controller.UserCenterController;
import com.lframework.starter.web.inner.controller.system.DefaultSysSelectorController;
import com.lframework.starter.web.inner.controller.system.OpLogController;
import com.lframework.starter.web.inner.controller.system.SysDataDicCategoryController;
import com.lframework.starter.web.inner.controller.system.SysDataDicController;
import com.lframework.starter.web.inner.controller.system.SysDataDicItemController;
import com.lframework.starter.web.inner.controller.system.SysDataPermissionDataController;
import com.lframework.starter.web.inner.controller.system.SysDataPermissionModelDetailController;
import com.lframework.starter.web.inner.controller.system.SysDeptController;
import com.lframework.starter.web.inner.controller.system.SysGenerateCodeController;
import com.lframework.starter.web.inner.controller.system.SysMailMessageController;
import com.lframework.starter.web.inner.controller.system.SysMenuController;
import com.lframework.starter.web.inner.controller.system.SysModuleController;
import com.lframework.starter.web.inner.controller.system.SysNoticeController;
import com.lframework.starter.web.inner.controller.system.SysNotifyGroupController;
import com.lframework.starter.web.inner.controller.system.SysOpenDomainController;
import com.lframework.starter.web.inner.controller.system.SysParameterController;
import com.lframework.starter.web.inner.controller.system.SysRoleCategoryController;
import com.lframework.starter.web.inner.controller.system.SysRoleController;
import com.lframework.starter.web.inner.controller.system.SysRoleMenuController;
import com.lframework.starter.web.inner.controller.system.SysSiteMessageController;
import com.lframework.starter.web.inner.controller.system.SysUserController;
import com.lframework.starter.web.inner.controller.system.SysUserGroupController;
import com.lframework.starter.web.inner.controller.system.SysUserRoleController;
import com.lframework.starter.web.inner.controller.system.TenantController;
import com.lframework.starter.web.inner.enums.system.SysDeptNodeType;
import com.lframework.starter.web.inner.handlers.exception.AccessDeniedExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.BindExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.ClientExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.ConstraintViolationExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.ExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.FileUploadExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.HttpMessageNotReadableExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.HttpRequestMethodNotSupportedExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.MethodArgumentNotValidExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.NotPermissionExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.SysExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.TypeMismatchExceptionConverter;
import com.lframework.starter.web.inner.handlers.exception.UnexpectedTypeExceptionConverter;
import com.lframework.starter.web.inner.impl.DefaultUserDetailsService;
import com.lframework.starter.web.inner.impl.DicCityServiceImpl;
import com.lframework.starter.web.inner.impl.GenerateCodeServiceImpl;
import com.lframework.starter.web.inner.impl.OpLogsServiceImpl;
import com.lframework.starter.web.inner.impl.OrderTimeLineServiceImpl;
import com.lframework.starter.web.inner.impl.QrtzServiceImpl;
import com.lframework.starter.web.inner.impl.RecursionMappingServiceImpl;
import com.lframework.starter.web.inner.impl.SecurityUploadRecordServiceImpl;
import com.lframework.starter.web.inner.impl.SysModuleServiceImpl;
import com.lframework.starter.web.inner.impl.SysModuleTenantServiceImpl;
import com.lframework.starter.web.inner.impl.TenantServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysDataDicCategoryServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysDataDicItemServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysDataDicServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysDataPermissionDataServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysDataPermissionModelDetailServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysDeptServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysGenerateCodeServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysMailMessageServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysMenuServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysNoticeLogServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysNoticeServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysNotifyGroupReceiverServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysNotifyGroupServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysOpenDomainServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysParameterServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysRoleCategoryServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysRoleMenuServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysRoleServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysSiteMessageServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysUserDeptServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysUserGroupDetailServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysUserGroupServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysUserRoleServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysUserServiceImpl;
import com.lframework.starter.web.inner.impl.system.SysUserTelephoneServiceImpl;
import com.lframework.starter.web.inner.listeners.OpLogTimerListener;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;

@Slf4j
@Configuration
@Import({
    WebExceptionHandler.class,
    DicCityServiceImpl.class,
    GenerateCodeServiceImpl.class,
    OpLogsServiceImpl.class,
    OrderTimeLineServiceImpl.class,
    RecursionMappingServiceImpl.class,
    SecurityUploadRecordServiceImpl.class,
    DefaultSysSelectorController.class,
    OpLogController.class,
    SysDataDicCategoryController.class,
    SysDataDicController.class,
    SysDataDicItemController.class,
    SysDataPermissionDataController.class,
    SysDataPermissionModelDetailController.class,
    SysDeptController.class,
    SysGenerateCodeController.class,
    SysMailMessageController.class,
    SysMenuController.class,
    SysModuleController.class,
    SysNoticeController.class,
    SysNotifyGroupController.class,
    SysOpenDomainController.class,
    SysParameterController.class,
    SysRoleCategoryController.class,
    SysRoleController.class,
    SysRoleMenuController.class,
    SysSiteMessageController.class,
    SysUserController.class,
    SysUserGroupController.class,
    SysUserRoleController.class,
    TenantController.class,
    AuthController.class,
    QrtzController.class,
    UserCenterController.class,
    SysDataDicCategoryServiceImpl.class,
    SysDataDicItemServiceImpl.class,
    SysDataDicServiceImpl.class,
    SysDataPermissionDataServiceImpl.class,
    SysDataPermissionModelDetailServiceImpl.class,
    SysDeptServiceImpl.class,
    SysGenerateCodeServiceImpl.class,
    SysMailMessageServiceImpl.class,
    SysMenuServiceImpl.class,
    SysNoticeLogServiceImpl.class,
    SysNoticeServiceImpl.class,
    SysNoticeServiceImpl.ReloadNoticeListener.class,
    SysNotifyGroupReceiverServiceImpl.class,
    SysNotifyGroupServiceImpl.class,
    SysOpenDomainServiceImpl.class,
    SysParameterServiceImpl.class,
    SysRoleCategoryServiceImpl.class,
    SysRoleMenuServiceImpl.class,
    SysRoleServiceImpl.class,
    SysSiteMessageServiceImpl.class,
    SysSiteMessageServiceImpl.ReloadSiteMessageListener.class,
    SysUserDeptServiceImpl.class,
    SysUserGroupDetailServiceImpl.class,
    SysUserGroupServiceImpl.class,
    SysUserRoleServiceImpl.class,
    SysUserServiceImpl.class,
    SysUserTelephoneServiceImpl.class,
    QrtzServiceImpl.class,
    SysModuleServiceImpl.class,
    SysModuleTenantServiceImpl.class,
    TenantServiceImpl.class,
    DefaultUserDetailsService.class
})
@MapperScan("com.lframework.starter.web.**.mappers")
public class WebBeanAutoConfiguration implements EnvironmentAware {

  @Bean
  public ControllerAspector controllerAspector() {

    return new ControllerAspector();
  }

  @Bean
  public OpenApiAspect openApiAspect() {
    return new OpenApiAspect();
  }

  @Bean
  public PermissionAspect permissionAspect() {
    return new PermissionAspect();
  }

  @Bean
  public UploadHandler cosUploadHandler() {
    return new CosUploadHandler();
  }

  @Bean
  public UploadHandler localUploadHandler() {
    return new LocalUploadHandler();
  }

  @Bean
  public UploadHandler obsUploadHandler() {
    return new ObsUploadHandler();
  }

  @Bean
  public UploadHandler ossUploadHandler() {
    return new OssUploadHandler();
  }

  @Bean
  public SecurityUploadHandler ossSecurityUploadHandler() {
    return new OssSecurityUploadHandler();
  }

  @Bean
  public SecurityUploadHandler cosSecurityUploadHandler() {
    return new CosSecurityUploadHandler();
  }

  @Bean
  public SecurityUploadHandler localSecurityUploadHandler() {
    return new LocalSecurityUploadHandler();
  }

  @Bean
  public SecurityUploadHandler obsSecurityUploadHandler() {
    return new ObsSecurityUploadHandler();
  }

  @Bean
  public ResponseErrorBuilder invokeResultBuilderWrapper() {
    return new InvokeResultErrorBuilderWrapper();
  }

  @Bean
  public CheckSignFactory defaultCheckSignFactory() {
    return new DefaultCheckSignFactory();
  }

  @Bean("cacheVariables")
  public CacheVariables cacheVariables() {
    return new CacheVariables();
  }

  @Bean
  public UserTokenResolverImpl userTokenResolver() {
    return new UserTokenResolverImpl();
  }

  @Bean
  public CurrentDateTimeRuleGenerateCodeHandler currentDateTimeRuleGenerateHandler() {
    return new CurrentDateTimeRuleGenerateCodeHandler();
  }

  @Bean
  public CustomRandomStrGenerateCodeRuleHandler customRandomStrGenerateRuleHandler() {
    return new CustomRandomStrGenerateCodeRuleHandler();
  }

  @Bean
  public FlowGenerateCodeRuleHandler flowGenerateRuleHandler() {
    return new FlowGenerateCodeRuleHandler();
  }

  @Bean
  public SnowFlakeGenerateCodeRuleHandler snowFlakeGenerateRuleHandler() {
    return new SnowFlakeGenerateCodeRuleHandler();
  }

  @Bean
  public StaticStrGenerateCodeRuleHandler staticStrGenerateRuleHandler() {
    return new StaticStrGenerateCodeRuleHandler();
  }

  @Bean
  public UUIDGenerateCodeRuleHandler uuidGenerateRuleHandler() {
    return new UUIDGenerateCodeRuleHandler();
  }

  @Bean
  public OrderDataPermissionDataPermissionType orderDataPermissionDataPermissionType() {
    return new OrderDataPermissionDataPermissionType();
  }

  @Bean
  public ProductDataPermissionDataPermissionType productDataPermissionDataPermissionType() {
    return new ProductDataPermissionDataPermissionType();
  }

  @Bean
  public ApprovePassOrderTimeLineBizType approvePassOrderTimeLineBizType() {
    return new ApprovePassOrderTimeLineBizType();
  }

  @Bean
  public ApproveReturnOrderTimeLineBizType approveReturnOrderTimeLineBizType() {
    return new ApproveReturnOrderTimeLineBizType();
  }

  @Bean
  public CancelApproveOrderTimeLineBizType cancelApproveOrderTimeLineBizType() {
    return new CancelApproveOrderTimeLineBizType();
  }

  @Bean
  public CreateOrderTimeLineBizType createOrderTimeLineBizType() {
    return new CreateOrderTimeLineBizType();
  }

  @Bean
  public NormalOrderTimeLineBizType normalOrderTimeLineBizType() {
    return new NormalOrderTimeLineBizType();
  }

  @Bean
  public SendOrderTimeLineBizType sendOrderTimeLineBizType() {
    return new SendOrderTimeLineBizType();
  }

  @Bean
  public UpdateOrderTimeLineBizType updateOrderTimeLineBizType() {
    return new UpdateOrderTimeLineBizType();
  }

  @Bean
  public AccessDeniedExceptionConverter accessDeniedExceptionConverter() {
    return new AccessDeniedExceptionConverter();
  }

  @Bean
  public BindExceptionConverter bindExceptionConverter() {
    return new BindExceptionConverter();
  }

  @Bean
  public ClientExceptionConverter clientExceptionConverter() {
    return new ClientExceptionConverter();
  }

  @Bean
  public ConstraintViolationExceptionConverter constraintViolationExceptionConverter() {
    return new ConstraintViolationExceptionConverter();
  }

  @Bean
  public ExceptionConverter exceptionConverter() {
    return new ExceptionConverter();
  }

  @Bean
  public FileUploadExceptionConverter fileUploadExceptionConverter() {
    return new FileUploadExceptionConverter();
  }

  @Bean
  public HttpMessageNotReadableExceptionConverter httpMessageNotReadableExceptionConverter() {
    return new HttpMessageNotReadableExceptionConverter();
  }

  @Bean
  public HttpRequestMethodNotSupportedExceptionConverter httpRequestMethodNotSupportedExceptionConverter() {
    return new HttpRequestMethodNotSupportedExceptionConverter();
  }

  @Bean
  public MethodArgumentNotValidExceptionConverter methodArgumentNotValidExceptionConverter() {
    return new MethodArgumentNotValidExceptionConverter();
  }

  @Bean
  public NotPermissionExceptionConverter notPermissionExceptionConverter() {
    return new NotPermissionExceptionConverter();
  }

  @Bean
  public SysExceptionConverter sysExceptionConverter() {
    return new SysExceptionConverter();
  }

  @Bean
  public TypeMismatchExceptionConverter typeMismatchExceptionConverter() {
    return new TypeMismatchExceptionConverter();
  }

  @Bean
  public UnexpectedTypeExceptionConverter unexpectedTypeExceptionConverter() {
    return new UnexpectedTypeExceptionConverter();
  }

  @Bean
  @ConditionalOnProperty(value = "op-logs.enabled", matchIfMissing = true)
  public OpLogAspector opLogAspector() {
    return new OpLogAspector();
  }

  @Bean
  public OrderTimeLineLogAspect orderTimeLineLogAspect() {
    return new OrderTimeLineLogAspect();
  }

  @Bean
  public OpLogTimerListener opLogTimerListener() {
    return new OpLogTimerListener();
  }

  @Bean
  public AuthOpLogType authOpLogType() {
    return new AuthOpLogType();
  }

  @Bean
  public SystemOpLogType systemOpLogType() {
    return new SystemOpLogType();
  }

  @Bean
  public OtherOpLogType otherOpLogType() {
    return new OtherOpLogType();
  }

  @Override
  public void setEnvironment(Environment environment) {
    log.info("web-starter加载完成");
  }

  @Bean
  public SysDeptNodeType sysDeptNodeType() {
    return new SysDeptNodeType();
  }
}
