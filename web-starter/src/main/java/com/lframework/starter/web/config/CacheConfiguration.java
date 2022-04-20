package com.lframework.starter.web.config;

import com.lframework.common.utils.ArrayUtil;
import lombok.SneakyThrows;
import net.oschina.j2cache.J2CacheBuilder;
import net.oschina.j2cache.J2CacheConfig;
import net.oschina.j2cache.springcache.J2CacheSpringCacheManageAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@EnableCaching
public class CacheConfiguration extends CachingConfigurerSupport {

  @Value("${j2cache.config-path:classpath:j2cache.properties}")
  private String configPath;

  @SneakyThrows
  @Override
  public CacheManager cacheManager() {

    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    Resource[] resources = resolver.getResources(configPath);
    if (ArrayUtil.isEmpty(resources) || !resources[0].exists()) {
      throw new RuntimeException("j2cache配置文件不存在！");
    }

    // 引入配置
    J2CacheConfig config = J2CacheConfig.initFromConfig(resources[0].getFile());
    // 生成 J2CacheBuilder
    J2CacheBuilder j2CacheBuilder = J2CacheBuilder.init(config);
    // 构建适配器
    J2CacheSpringCacheManageAdapter j2CacheSpringCacheManageAdapter = new J2CacheSpringCacheManageAdapter(
        j2CacheBuilder, true);

    return j2CacheSpringCacheManageAdapter;
  }
}
