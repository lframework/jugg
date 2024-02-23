package com.lframework.starter.web.utils;

import com.lframework.starter.web.common.utils.ApplicationUtil;
import com.lframework.starter.web.service.GroovySupportService;
import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
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
   * 执行脚本
   *
   * @param script
   * @return
   */
  public static Object excuteScript(String script) {

    GroovyScript groovyScript = new GroovyScript(script);
    return groovyScript.run();
  }

  public static class GroovyScript extends Script {

    private String script;

    public GroovyScript() {
    }

    public GroovyScript(String script) {
      this.script = script;
    }

    @Override
    public Object run() {
      GroovyShell shell = getShell();
      return shell.evaluate(script);
    }
  }
}
