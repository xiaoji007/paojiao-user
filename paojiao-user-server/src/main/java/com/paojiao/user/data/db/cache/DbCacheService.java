package com.paojiao.user.data.db.cache;

import com.fission.cache.bean.CacheBean;
import com.fission.cache.service.AbstractCacheService;
import com.fission.utils.tool.StringUtil;
import com.paojiao.user.data.db.IUserPicReadDao;
import com.paojiao.user.data.db.IUserPicWriteDao;
import com.paojiao.user.util.ConstUtil;
import com.paojiao.user.util.RedisUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Component
public class DbCacheService extends AbstractCacheService {

    private static final List<Class<?>> CACHE_CLASS_LIST = new ArrayList<>();
    private static final LinkedBlockingQueue<String> expireQueue = new LinkedBlockingQueue<>();

    static {
        DbCacheService.CACHE_CLASS_LIST.add(IUserPicReadDao.class);
        DbCacheService.CACHE_CLASS_LIST.add(IUserPicWriteDao.class);
    }

    @Inject
    @Named(ConstUtil.NameUtil.REDIS_USER)
    private RedisTemplate redis;

    public DbCacheService() {
        super(1, 10, 30 * 1000L, 10 * 1000L, 0, 60 * 1000L, DbCacheService.CACHE_CLASS_LIST);
    }

    @Scheduled(fixedDelay = 10000)
    public void expireTask() {
        try {
            while (true) {
                String key = expireQueue.poll(1, TimeUnit.MILLISECONDS);
                if (StringUtil.isBlank(key)) {
                    break;
                }
                String cacheLockKey = key + "_lock";
                RedisUtil.redisValueLocks(this.redis, cacheLockKey, (ValueOperations valueOperations) -> {
                    CacheBean cache = (CacheBean) valueOperations.get(key);
                    if (null == cache) {
                        return true;
                    }
                    cache.setLastGetTime(System.currentTimeMillis());
                    cache.setExpireTime(cache.getExpireTime() + cache.getGetExpireTime());
                    long expireTime = cache.getExpireTime() - System.currentTimeMillis();
                    if (expireTime <= 0) {
                        this.redis.delete(key);
                    } else {
                        valueOperations.set(key, cache, expireTime, TimeUnit.MILLISECONDS);
                    }
                    return true;
                });
            }
        } catch (InterruptedException e) {

        }
    }

    @Override
    public CacheBean getCache(String key) {
        try {
            CacheBean cache = (CacheBean) this.redis.opsForValue().get(key);
            if (null == cache) {
                return null;
            }
            if (cache.getExpireTime() < System.currentTimeMillis()) {
                this.removeCache2(key);
                return null;
            }
            if (!expireQueue.contains(key)) {
                expireQueue.add(key);
            }
            return cache;
        } catch (Exception e) {
            this.redis.delete(key);
            return null;
        }
    }

    @Override
    public List<CacheBean> multiGetCache(Collection<String> keys) {
        List<CacheBean> cacheBeans = this.redis.opsForValue().multiGet(keys);
        if (cacheBeans == null || cacheBeans.contains(null)) {
            return null;
        }
        keys.parallelStream().forEach((String key) -> {
            if (StringUtil.isNotBlank(key)) {
                if (!expireQueue.contains(key)) {
                    expireQueue.add(key);
                }
            }
        });
        return cacheBeans;
    }

    @Override
    public void addCache(CacheBean cache) {
        if (null == cache) {
            return;
        }
        this.redis.opsForValue().set(cache.getKey(), cache, cache.getExpireTime() - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void updateCache(CacheBean cache) {
        if (null == cache) {
            return;
        }
        String cacheLockKey = cache.getKey() + "_lock";
        RedisUtil.redisValueLocks(this.redis, cacheLockKey, (ValueOperations valueOperations) -> {
            CacheBean cacheTemp = this.getCache(cache.getKey());
            if (null == cacheTemp || cacheTemp.getLastUpdateTime() != cache.getLastUpdateTime() || cacheTemp.getExpireTime() < System.currentTimeMillis()) {
                this.removeCache2(cache.getKey());
            } else {
                long expireTime = cacheTemp.getExpireTime() + cacheTemp.getUpdateExpireTime() - System.currentTimeMillis();
                valueOperations.set(cache.getKey(), cache, expireTime, TimeUnit.MILLISECONDS);
            }
            return true;
        });
    }

    @Override
    public CacheBean removeCache(String key) {
        String cacheLockKey = key + "_lock";
        return RedisUtil.redisValueLocks(this.redis, cacheLockKey, (ValueOperations valueOperations) -> {
            CacheBean cacheBean = (CacheBean) this.redis.opsForValue().get(key);
            this.removeCache2(key);
            return cacheBean;
        });
    }

    public void removeCache2(String key) {
        this.redis.delete(key);
    }
}






