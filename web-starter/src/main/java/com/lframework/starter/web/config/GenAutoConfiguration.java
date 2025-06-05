package com.lframework.starter.web.config;

import com.lframework.starter.web.gen.builders.CustomListBuilder;
import com.lframework.starter.web.gen.builders.CustomPageBuilder;
import com.lframework.starter.web.gen.builders.CustomSelectorBuilder;
import com.lframework.starter.web.gen.builders.DataEntityBuilder;
import com.lframework.starter.web.gen.builders.DataObjectBuilder;
import com.lframework.starter.web.gen.controller.GenController;
import com.lframework.starter.web.gen.controller.GenCustomListCategoryController;
import com.lframework.starter.web.gen.controller.GenCustomListController;
import com.lframework.starter.web.gen.controller.GenCustomPageCategoryController;
import com.lframework.starter.web.gen.controller.GenCustomPageController;
import com.lframework.starter.web.gen.controller.GenCustomSelectorCategoryController;
import com.lframework.starter.web.gen.controller.GenCustomSelectorController;
import com.lframework.starter.web.gen.controller.GenDataEntityCategoryController;
import com.lframework.starter.web.gen.controller.GenDataEntityController;
import com.lframework.starter.web.gen.controller.GenDataObjCategoryController;
import com.lframework.starter.web.gen.controller.GenDataObjController;
import com.lframework.starter.web.gen.controller.GenSelectorController;
import com.lframework.starter.web.gen.converters.GenMysqlDataTypeConverter;
import com.lframework.starter.web.gen.converters.GenViewTypeConverter;
import com.lframework.starter.web.gen.impl.GenCreateColumnConfigServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomListCategoryServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomListDetailServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomListHandleColumnServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomListQueryParamsServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomListServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomListToolbarServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomPageCategoryServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomPageServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomSelectorCategoryServiceImpl;
import com.lframework.starter.web.gen.impl.GenCustomSelectorServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataEntityCategoryServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataEntityDetailServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataEntityServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataObjCategoryServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataObjDetailServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataObjQueryDetailServiceImpl;
import com.lframework.starter.web.gen.impl.GenDataObjServiceImpl;
import com.lframework.starter.web.gen.impl.GenDetailColumnConfigServiceImpl;
import com.lframework.starter.web.gen.impl.GenQueryColumnConfigServiceImpl;
import com.lframework.starter.web.gen.impl.GenQueryParamsColumnConfigServiceImpl;
import com.lframework.starter.web.gen.impl.GenUpdateColumnConfigServiceImpl;
import com.lframework.starter.web.gen.impl.GenerateInfoServiceImpl;
import com.lframework.starter.web.gen.impl.SimpleDBServiceImpl;
import com.lframework.starter.web.gen.impl.SimpleTableColumnServiceImpl;
import com.lframework.starter.web.gen.listeners.CreateColumnConfigListener;
import com.lframework.starter.web.gen.listeners.DetailColumnConfigListener;
import com.lframework.starter.web.gen.listeners.GenCustomListListener;
import com.lframework.starter.web.gen.listeners.GenCustomSelectorListener;
import com.lframework.starter.web.gen.listeners.GenDataObjListener;
import com.lframework.starter.web.gen.listeners.GenTenantListener;
import com.lframework.starter.web.gen.listeners.GenerateInfoListener;
import com.lframework.starter.web.gen.listeners.QueryColumnConfigListener;
import com.lframework.starter.web.gen.listeners.QueryParamsColumnConfigListener;
import com.lframework.starter.web.gen.listeners.UpdateColumnConfigListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({
    GenController.class,
    GenCustomListCategoryController.class,
    GenCustomListController.class,
    GenCustomPageCategoryController.class,
    GenCustomPageController.class,
    GenCustomSelectorCategoryController.class,
    GenCustomSelectorController.class,
    GenDataEntityCategoryController.class,
    GenDataEntityController.class,
    GenDataObjCategoryController.class,
    GenDataObjController.class,
    GenSelectorController.class,
    GenCreateColumnConfigServiceImpl.class,
    GenCustomListCategoryServiceImpl.class,
    GenCustomListDetailServiceImpl.class,
    GenCustomListHandleColumnServiceImpl.class,
    GenCustomListQueryParamsServiceImpl.class,
    GenCustomListServiceImpl.class,
    GenCustomListToolbarServiceImpl.class,
    GenCustomPageCategoryServiceImpl.class,
    GenCustomPageServiceImpl.class,
    GenCustomSelectorCategoryServiceImpl.class,
    GenCustomSelectorServiceImpl.class,
    GenDataEntityCategoryServiceImpl.class,
    GenDataEntityDetailServiceImpl.class,
    GenDataEntityServiceImpl.class,
    GenDataObjCategoryServiceImpl.class,
    GenDataObjDetailServiceImpl.class,
    GenDataObjQueryDetailServiceImpl.class,
    GenDataObjServiceImpl.class,
    GenDetailColumnConfigServiceImpl.class,
    GenerateInfoServiceImpl.class,
    GenQueryColumnConfigServiceImpl.class,
    GenQueryParamsColumnConfigServiceImpl.class,
    GenUpdateColumnConfigServiceImpl.class,
    SimpleDBServiceImpl.class,
    SimpleTableColumnServiceImpl.class
})
public class GenAutoConfiguration {

  @Bean
  public CustomListBuilder customListBuilder() {
    return new CustomListBuilder();
  }

  @Bean
  public CustomPageBuilder customPageBuilder() {
    return new CustomPageBuilder();
  }

  @Bean
  public CustomSelectorBuilder customSelectorBuilder() {
    return new CustomSelectorBuilder();
  }

  @Bean
  public DataEntityBuilder dataEntityBuilder() {
    return new DataEntityBuilder();
  }

  @Bean
  public DataObjectBuilder dataObjectBuilder() {
    return new DataObjectBuilder();
  }

  @Bean
  public GenMysqlDataTypeConverter genMysqlDataTypeConverter() {
    return new GenMysqlDataTypeConverter();
  }

  @Bean
  public GenViewTypeConverter genViewTypeConverter() {
    return new GenViewTypeConverter();
  }

  @Bean
  public CreateColumnConfigListener.DeleteEntityDetailListener createColumnConfigDeleteEntityDetailListener() {
    return new CreateColumnConfigListener.DeleteEntityDetailListener();
  }

  @Bean
  public CreateColumnConfigListener.DeleteEntityListener deleteEntityListener() {
    return new CreateColumnConfigListener.DeleteEntityListener();
  }

  @Bean
  public DetailColumnConfigListener.DeleteEntityDetailListener detailColumnConfigDeleteEntityDetailListener() {
    return new DetailColumnConfigListener.DeleteEntityDetailListener();
  }

  @Bean
  public DetailColumnConfigListener.DeleteEntityListener detailColumnConfigDeleteEntityListener() {
    return new DetailColumnConfigListener.DeleteEntityListener();
  }

  @Bean
  public GenCustomListListener.DataEntityDetailDeleteListener genCustomListDataEntityDetailDeleteListener() {
    return new GenCustomListListener.DataEntityDetailDeleteListener();
  }

  @Bean
  public GenCustomListListener.DataObjDeleteListener genCustomListDataObjDeleteListener() {
    return new GenCustomListListener.DataObjDeleteListener();
  }

  @Bean
  public GenCustomListListener.DataObjQueryDetailDeleteListener genCustomListDataObjQueryDetailDeleteListener() {
    return new GenCustomListListener.DataObjQueryDetailDeleteListener();
  }

  @Bean
  public GenCustomSelectorListener.CustomListDeleteListener genCustomSelectorCustomListDeleteListener() {
    return new GenCustomSelectorListener.CustomListDeleteListener();
  }

  @Bean
  public GenDataObjListener.DataEntityDeleteListener genDataObjDataEntityDeleteListener() {
    return new GenDataObjListener.DataEntityDeleteListener();
  }

  @Bean
  public GenDataObjListener.DataEntityDetailDeleteListener genDataObjDataEntityDetailDeleteListener() {
    return new GenDataObjListener.DataEntityDetailDeleteListener();
  }

  @Bean
  public GenerateInfoListener.DeleteEntityListener generateInfoDeleteEntityListener() {
    return new GenerateInfoListener.DeleteEntityListener();
  }

  @Bean
  public GenTenantListener.ReloadTenantListener genTenantReloadTenantListener() {
    return new GenTenantListener.ReloadTenantListener();
  }

  @Bean
  public QueryColumnConfigListener.DeleteEntityDetailListener queryColumnConfigDeleteEntityDetailListener() {
    return new QueryColumnConfigListener.DeleteEntityDetailListener();
  }

  @Bean
  public QueryColumnConfigListener.DeleteEntityListener queryColumnConfigDeleteEntityListener() {
    return new QueryColumnConfigListener.DeleteEntityListener();
  }

  @Bean
  public QueryParamsColumnConfigListener.DeleteEntityDetailListener queryParamsColumnConfigDeleteEntityDetailListener() {
    return new QueryParamsColumnConfigListener.DeleteEntityDetailListener();
  }

  @Bean
  public QueryParamsColumnConfigListener.DeleteEntityListener queryParamsColumnConfigDeleteEntityListener() {
    return new QueryParamsColumnConfigListener.DeleteEntityListener();
  }

  @Bean
  public UpdateColumnConfigListener.DeleteEntityDetailListener updateColumnConfigDeleteEntityDetailListener() {
    return new UpdateColumnConfigListener.DeleteEntityDetailListener();
  }

  @Bean
  public UpdateColumnConfigListener.DeleteEntityListener updateColumnConfigDeleteEntityListener() {
    return new UpdateColumnConfigListener.DeleteEntityListener();
  }
}
