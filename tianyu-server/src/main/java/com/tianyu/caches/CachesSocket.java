package com.tianyu.caches;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Socket 缓存
 */
public class CachesSocket {

    /**
     * 推送记录
     */
    final static Cache<String, PushInfoCache> PUSH_INFO_CACHE = CacheBuilder.newBuilder()
            .expireAfterWrite(4, TimeUnit.MINUTES)
            .initialCapacity(1000)
            .concurrencyLevel(2)
            .maximumSize(100000)
            .build();


    public static PushInfoCache getPushInfoCache(String token) {
        return PUSH_INFO_CACHE.getIfPresent(token);
    }

    public static void setPushInfoCache(String token, PushInfoCache pushInfoCache) {
        PUSH_INFO_CACHE.put(token, pushInfoCache);
    }
}
