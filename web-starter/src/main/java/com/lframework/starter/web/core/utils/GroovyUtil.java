package com.lframework.starter.web.core.utils;

import com.lframework.starter.web.inner.service.GroovySupportService;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.util.Map;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * Groovy脚本工具类
 * 提供Groovy脚本执行功能，支持动态脚本解析和执行
 * 包括脚本执行、变量绑定、脚本类加载等功能
 *
 * @author lframework@163.com
 * @since 2022/8/20
 */
public class GroovyUtil {

  private static GroovyShell SHELL;

  /**
   * 获取GroovyShell实例
   * 单例模式获取配置好的GroovyShell实例
   *
   * @return GroovyShell实例
   */
  private static GroovyShell getShell() {
    if (SHELL == null) {
      synchronized (GroovyUtil.class) {
        if (SHELL != null) {
          return SHELL;
        }

        Binding binding = new Binding();

        Map<String, GroovySupportService> beans = ApplicationUtil.getBeansOfType(GroovySupportService.class);
        beans.forEach(binding::setVariable);

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(
            GroovyScript.class.getClassLoader());
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        compilerConfiguration.setScriptBaseClass(GroovyScript.class.getName());

        SHELL = new GroovyShell(groovyClassLoader, binding, compilerConfiguration);
      }
    }

    return SHELL;
  }

  /**
   * 执行Groovy脚本
   * 执行指定的Groovy脚本代码并返回结果
   *
   * @param script 要执行的Groovy脚本代码，不能为null
   * @return 脚本执行结果
   */
  public static Object excuteScript(String script) {

    GroovyScript groovyScript = new GroovyScript(script);
    return groovyScript.run();
  }

  /**
   * Groovy脚本执行类
   * 封装Groovy脚本的执行逻辑
   */
  public static class GroovyScript extends Script {

    /**
     * 脚本代码
     */
    private String script;

    /**
     * 默认构造函数
     */
    public GroovyScript() {
    }

    /**
     * 带脚本代码的构造函数
     *
     * @param script 脚本代码，不能为null
     */
    public GroovyScript(String script) {
      this.script = script;
    }

    /**
     * 执行脚本
     *
     * @return 脚本执行结果
     */
    @Override
    public Object run() {
      GroovyShell shell = getShell();
      return shell.evaluate(script);
    }
  }
}
