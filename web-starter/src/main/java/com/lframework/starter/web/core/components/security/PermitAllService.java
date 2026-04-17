package com.lframework.starter.web.core.components.security;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.config.properties.UploadProperties;
import com.lframework.starter.web.config.properties.WebProperties;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher.Builder;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PermitAllService {

  @Autowired
  private WebProperties webProperties;

  @Autowired
  private UploadProperties uploadProperties;

  private List<Entry<HttpMethod, String>> permitAllUrls;

  private Set<RequestMatcher> matchers;

  private final Builder matcherBuilder = PathPatternRequestMatcher.withDefaults();

  @PostConstruct
  public void init() {

    List<Entry<HttpMethod, String>> results = new ArrayList<>();
    String filterUrl = webProperties.getFilterUrl();
    if (StringUtil.isNotEmpty(filterUrl)) {
      String[] filterUrls = filterUrl.split(",");
      for (String url : filterUrls) {
        // 配置文件配置的url所有method都放行
        results.add(new SimpleEntry<>(null, url));
      }
    }

    String uploadUrl = uploadProperties.getUrl();
    // 访问上传文件url
    results.add(new SimpleEntry<>(HttpMethod.GET,
        uploadUrl.endsWith("/") ? uploadUrl + "**" : uploadUrl + "/**"));
    // swagger
    results.add(new SimpleEntry<>(null, "/doc.html"));
    results.add(new SimpleEntry<>(null, "/webjars/**"));
    results.add(new SimpleEntry<>(null, "/v3/api-docs"));
    results.add(new SimpleEntry<>(null, "/v3/api-docs/**"));

    this.permitAllUrls = results;

    this.matchers = new CopyOnWriteArraySet<>();

    for (Entry<HttpMethod, String> permitAllUrl : this.permitAllUrls) {
      this.matchers.add(buildMatcher(permitAllUrl.getKey(), permitAllUrl.getValue()));
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

    this.matchers.add(buildMatcher(HttpMethod.valueOf(request.getMethod()), getRequestPath(request)));
  }

  private RequestMatcher buildMatcher(HttpMethod method, String pathPattern) {
    if (method == null) {
      return this.matcherBuilder.matcher(pathPattern);
    }
    return this.matcherBuilder.matcher(method, pathPattern);
  }

  private String getRequestPath(HttpServletRequest request) {
    String pathInfo = request.getPathInfo();
    if (StringUtil.isEmpty(pathInfo)) {
      return request.getServletPath();
    }
    return request.getServletPath() + pathInfo;
  }
}
