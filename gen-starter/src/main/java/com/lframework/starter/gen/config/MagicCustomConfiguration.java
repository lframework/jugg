package com.lframework.starter.gen.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import java.util.Map;
import java.util.Map.Entry;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.ssssssss.magicapi.datasource.model.MagicDynamicDataSource;

@Configuration
public class MagicCustomConfiguration {

  @Bean
  public TaskScheduler taskScheduler() {
    ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    taskScheduler.setPoolSize(10);
    taskScheduler.initialize();
    return taskScheduler;
  }

  @Bean
  public MagicDynamicDataSource magicDynamicDataSource(DynamicRoutingDataSource dataSource) {
    Map<String, DataSource> dataSourceMap = dataSource.getDataSources();
    MagicDynamicDataSource dynamicDataSource = new MagicDynamicDataSource();
    dynamicDataSource.setDefault(dataSourceMap.get("master"));
    for (Entry<String, DataSource> entry : dataSourceMap.entrySet()) {
      if ("master".equals(entry.getKey())) {
        continue;
      }
      dynamicDataSource.add(entry.getKey(), entry.getValue());
    }

    return dynamicDataSource;
  }
}
