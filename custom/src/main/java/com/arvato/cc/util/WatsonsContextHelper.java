package com.arvato.cc.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: pang008
 * Date: 27/09/12
 * Time: 15:53
 * To change this template use File | Settings | File Templates.
 */
public class WatsonsContextHelper implements ApplicationContextAware {

      private static ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) {
        WatsonsContextHelper.applicationContext = applicationContext; //NOSONAR
    }

    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> requiredType) {
        checkApplicationContext();
        Map beanMap = applicationContext.getBeansOfType(requiredType);
        for (Object beanName : beanMap.keySet()) {
            return (T) beanMap.get(beanName);
        }
        return null;
    }

    public static <T> T getBean(String name, Object ... values) {
        checkApplicationContext();
        return (T) applicationContext.getBean(name, values);
    }

    public static void cleanApplicationContext() {
        applicationContext = null;
    }

    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
