package com.paojiao.user.data.db.cache.util;

import com.fission.cache.util.CacheAop;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Aspect
@Service
@Order(1)
public class CacheDBAspect {

    private final CacheAop cacheAop = new CacheAop();

    @AfterReturning(pointcut = "execution(public * com.paojiao.user.service.impl.*.*(..))")
    public void cacheService() throws Throwable {
        this.cacheAop.afterService();
    }
}
