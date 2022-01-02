package com.lframework.starter.web.config;

import groovy.lang.Binding;
import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class GroovyEngineConfiguration implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    public Binding getBinding() {

        Binding groovyBinding = new Binding();

        Map<String, Object> beanMap = applicationContext.getBeansOfType(Object.class);
        for (String beanName : beanMap.keySet()) {
            groovyBinding.setVariable(beanName, beanMap.get(beanName));
        }
        return groovyBinding;
    }

    @Bean
    public GroovyShell getGroovyShell(Binding binding) {

        GroovyClassLoader groovyClassLoader = new GroovyClassLoader(this.getClass().getClassLoader());
        CompilerConfiguration compilerConfiguration = new CompilerConfiguration();
        compilerConfiguration.setSourceEncoding("utf-8");
        compilerConfiguration.setScriptBaseClass(this.getClass().getName());

        return new GroovyShell(groovyClassLoader, binding, compilerConfiguration);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = applicationContext;
    }
}
