package com.lframework.starter.web.utils;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import java.util.Map;
import org.codehaus.groovy.control.CompilerConfiguration;

/**
 * @author zmj
 * @since 2022/8/20
 */
public class GroovyUtil {

  private static GroovyShell SHELL;

  /**
   * 获取Shell
   *
   * @return
   */
  private static GroovyShell getShell() {
    if (SHELL == null) {
      synchronized (GroovyUtil.class) {
        if (SHELL != null) {
          return SHELL;
        }

        Binding binding = new Binding();

        Map<String, Object> beans = ApplicationUtil.getBeansOfType(Object.class);
        beans.forEach(binding::setVariable);

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(
            GroovyUtil.class.getClassLoader());
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        compilerConfiguration.setScriptBaseClass(GroovyUtil.class.getName());

        SHELL = new GroovyShell(groovyClassLoader, binding, compilerConfiguration);
      }
    }

    return SHELL;
  }

  /**
   * 执行脚本
   *
   * @param script
   * @return
   */
  public static Object excuteScript(String script) {
    GroovyShell shell = getShell();
    return shell.evaluate(script);
  }
}
