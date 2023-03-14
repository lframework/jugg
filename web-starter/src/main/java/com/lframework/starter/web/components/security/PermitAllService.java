package com.lframework.starter.web.components.security;

import com.lframework.starter.common.utils.StringUtil;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

public class PermitAllService {

  /**
   * 不需要认证的Url 默认为空
   */
  @Value("${filter-url:}")
  private String filterUrl;

  @Value("${upload.url}")
  private String uploadUrl;

  private List<Entry<HttpMethod, String>> permitAllUrls;

  private Set<AntPathRequestMatcher> matchers;

  @PostConstruct
  public void init() {

    List<Entry<HttpMethod, String>> results = new ArrayList<>();
    if (StringUtil.isNotEmpty(this.filterUrl)) {
      String[] filterUrls = filterUrl.split(",");
      for (String url : filterUrls) {
        // 配置文件配置的url所有method都放行
        results.add(new SimpleEntry<>(null, url));
      }
    }

    // 访问上传文件url
    results.add(new SimpleEntry<>(HttpMethod.GET,
        uploadUrl.endsWith("/") ? uploadUrl + "**" : uploadUrl + "/**"));
    // swagger
    results.add(new SimpleEntry<>(null, "/doc.html"));
    results.add(new SimpleEntry<>(null, "/webjars/**"));
    results.add(new SimpleEntry<>(null, "/v2/api-docs"));
    results.add(new SimpleEntry<>(null, "/swagger-resources"));
    results.add(new SimpleEntry<>(null, "/swagger-resources/configuration/ui"));
    results.add(new SimpleEntry<>(null, "/swagger-resources/configuration/security"));
    results.add(new SimpleEntry<>(null, "/v2/api-docs-ext"));

    this.permitAllUrls = results;

    this.matchers = new CopyOnWriteArraySet<>();

    for (Entry<HttpMethod, String> permitAllUrl : this.permitAllUrls) {
      this.matchers.add(new AntPathRequestMatcher(permitAllUrl.getValue(),
          permitAllUrl.getKey() == null ? null : permitAllUrl.getKey().toString()));
    }
  }

  /**
   * 获取不需要认证的url
   *
   * @return
   */
  public List<Entry<HttpMethod, String>> getUrls() {

    return this.permitAllUrls;
  }

  public boolean isMatch(HttpServletRequest request) {

    return this.matchers.stream().anyMatch(t -> t.matches(request));
  }

  public void addMatch(HttpServletRequest request) {
    this.matchers.add(new AntPathRequestMatcher(request.getRequestURI(),
        request.getMethod()));
  }
}
