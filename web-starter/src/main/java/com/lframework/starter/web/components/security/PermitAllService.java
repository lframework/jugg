package com.lframework.starter.web.components.security;

import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.StringUtil;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
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

  private List<AntPathRequestMatcher> matchers;

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

    // 登录
    results.add(new SimpleEntry<>(HttpMethod.POST, StringPool.LOGIN_API_URL));
    // 获取验证码
    results.add(new SimpleEntry<>(HttpMethod.GET, StringPool.CAPTCHA_URL));
    // 退出登录
    results.add(new SimpleEntry<>(HttpMethod.POST, StringPool.LOGOUT_API_URL));
    // 系统初始化
    results.add(new SimpleEntry<>(HttpMethod.GET, StringPool.AUTH_INIT_URL));
    // 注册
    results.add(new SimpleEntry<>(HttpMethod.POST, StringPool.AUTH_REGIST_URL));
    // 访问上传文件url
    results.add(new SimpleEntry<>(HttpMethod.GET,
        uploadUrl.endsWith("/") ? uploadUrl + "**" : uploadUrl + "/**"));
    // 忘记密码
    results.add(new SimpleEntry<>(HttpMethod.GET, "/auth/forget/**"));
    results.add(new SimpleEntry<>(HttpMethod.POST, "/auth/forget/**"));
    // swagger
    results.add(new SimpleEntry<>(null, "/doc.html"));
    results.add(new SimpleEntry<>(null, "/webjars/**"));
    results.add(new SimpleEntry<>(null, "/v2/api-docs"));
    results.add(new SimpleEntry<>(null, "/swagger-resources"));
    results.add(new SimpleEntry<>(null, "/swagger-resources/configuration/ui"));
    results.add(new SimpleEntry<>(null, "/swagger-resources/configuration/security"));
    results.add(new SimpleEntry<>(null, "/v2/api-docs-ext"));

    this.permitAllUrls = results;

    this.matchers = new ArrayList<>();

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
}
