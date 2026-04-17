package com.lframework.starter.web.config;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import com.github.xiaoymin.knife4j.spring.extension.Knife4jOpenApiCustomizer;
import com.github.xiaoymin.knife4j.spring.extension.OpenApiExtensionResolver;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.models.OpenAPI;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

@AutoConfiguration(beforeName = "com.github.xiaoymin.knife4j.spring.configuration.Knife4jAutoConfiguration")
@ConditionalOnClass(Knife4jOpenApiCustomizer.class)
@ConditionalOnProperty(value = "knife4j.enable", havingValue = "true")
public class Knife4jCompatibilityAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean(Knife4jOpenApiCustomizer.class)
  public Knife4jOpenApiCustomizer knife4jOpenApiCustomizer(Knife4jProperties knife4jProperties,
      SpringDocConfigProperties springDocConfigProperties) {

    return new CompatibleKnife4jOpenApiCustomizer(knife4jProperties, springDocConfigProperties);
  }

  static class CompatibleKnife4jOpenApiCustomizer extends Knife4jOpenApiCustomizer {

    private final Knife4jProperties knife4jProperties;

    private final SpringDocConfigProperties springDocConfigProperties;

    CompatibleKnife4jOpenApiCustomizer(Knife4jProperties knife4jProperties,
        SpringDocConfigProperties springDocConfigProperties) {

      super(knife4jProperties, springDocConfigProperties);
      this.knife4jProperties = knife4jProperties;
      this.springDocConfigProperties = springDocConfigProperties;
    }

    @Override
    public void customise(OpenAPI openApi) {
      if (!this.knife4jProperties.isEnable()) {
        return;
      }

      OpenApiExtensionResolver resolver = new OpenApiExtensionResolver(
          this.knife4jProperties.getSetting(), this.knife4jProperties.getDocuments());
      resolver.start();

      Map<String, Object> extensions = new HashMap<>();
      extensions.put("x-setting", this.knife4jProperties.getSetting());
      extensions.put("x-markdownFiles", resolver.getMarkdownFiles());
      openApi.addExtension("x-openapi", extensions);

      addOrderExtension(openApi);
    }

    private void addOrderExtension(OpenAPI openApi) {
      Set<SpringDocConfigProperties.GroupConfig> groupConfigs =
          this.springDocConfigProperties.getGroupConfigs();
      if (CollectionUtils.isEmpty(groupConfigs)) {
        return;
      }

      Set<String> packagesToScan = groupConfigs.stream()
          .map(SpringDocConfigProperties.GroupConfig::getPackagesToScan)
          .filter(packages -> !CollectionUtils.isEmpty(packages))
          .flatMap(Collection::stream)
          .collect(Collectors.toSet());
      if (CollectionUtils.isEmpty(packagesToScan)) {
        return;
      }

      Set<Class<?>> apiSupportClasses = packagesToScan.stream()
          .map(this::scanRestControllers)
          .flatMap(Set::stream)
          .filter(clazz -> clazz.isAnnotationPresent(ApiSupport.class))
          .collect(Collectors.toSet());
      if (CollectionUtils.isEmpty(apiSupportClasses)) {
        return;
      }

      Map<String, Integer> tagOrders = new HashMap<>();
      apiSupportClasses.forEach(clazz -> {
        String tagName = getTagName(clazz);
        if (tagName != null) {
          tagOrders.putIfAbsent(tagName, clazz.getAnnotation(ApiSupport.class).order());
        }
      });

      if (CollectionUtils.isEmpty(tagOrders) || openApi.getTags() == null) {
        return;
      }

      openApi.getTags().forEach(tag -> {
        Integer order = tagOrders.get(tag.getName());
        if (order != null) {
          tag.addExtension("x-order", order);
        }
      });
    }

    private Set<Class<?>> scanRestControllers(String packageName) {
      ClassPathScanningCandidateComponentProvider provider =
          new ClassPathScanningCandidateComponentProvider(false);
      provider.addIncludeFilter(new AnnotationTypeFilter(RestController.class));

      Set<Class<?>> classes = new HashSet<>();
      for (BeanDefinition candidate : provider.findCandidateComponents(packageName)) {
        try {
          classes.add(Class.forName(candidate.getBeanClassName()));
        } catch (ClassNotFoundException ignored) {
          // Ignore classes that are not loadable in the current runtime.
        }
      }
      return classes;
    }

    private String getTagName(Class<?> clazz) {
      Tag tag = clazz.getAnnotation(Tag.class);
      if (tag != null) {
        return tag.name();
      }

      for (Class<?> interfaceClass : clazz.getInterfaces()) {
        Tag interfaceTag = interfaceClass.getAnnotation(Tag.class);
        if (interfaceTag != null) {
          return interfaceTag.name();
        }
      }

      return null;
    }
  }
}
