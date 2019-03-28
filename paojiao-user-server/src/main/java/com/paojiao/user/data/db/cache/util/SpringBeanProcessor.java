package com.paojiao.user.data.db.cache.util;

import com.fission.cache.proxy.CacheProxy;
import com.paojiao.user.data.db.impl.UserPicReadDaoImpl;
import com.paojiao.user.data.db.impl.UserPicWriteDaoImpl;
import com.paojiao.user.data.db.impl.UserReadDaoImpl;
import com.paojiao.user.data.db.impl.UserWriteDaoImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (UserWriteDaoImpl.class.isAssignableFrom(bean.getClass())) {
            return CacheProxy.getCacheProxy(bean);
        }
        if (UserReadDaoImpl.class.isAssignableFrom(bean.getClass())) {
            return CacheProxy.getCacheProxy(bean);
        }
        if (UserPicWriteDaoImpl.class.isAssignableFrom(bean.getClass())) {
            return CacheProxy.getCacheProxy(bean);
        }
        if (UserPicReadDaoImpl.class.isAssignableFrom(bean.getClass())) {
            return CacheProxy.getCacheProxy(bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
