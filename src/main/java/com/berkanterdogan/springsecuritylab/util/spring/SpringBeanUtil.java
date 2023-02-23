package com.berkanterdogan.springsecuritylab.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SpringBeanUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }

    /**
     * Get Bean from application context
     *
     * @param beanClass - class of the Bean
     * @param <T> - type of bean
     * @return - object bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return Objects.nonNull(applicationContext) ? applicationContext.getBean(beanClass) : null;
    }
}
