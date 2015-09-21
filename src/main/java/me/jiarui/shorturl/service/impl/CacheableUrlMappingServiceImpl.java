package me.jiarui.shorturl.service.impl;

import com.google.common.base.Optional;
import me.jiarui.shorturl.common.GlobalConfig;
import me.jiarui.shorturl.entity.LongUrl;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.entity.UrlMapping;
import me.jiarui.shorturl.service.UrlMappingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.*;

import javax.annotation.PreDestroy;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Sher10ck on 8/8/15.
 */
@Service("cacheableUrlMappingServiceImpl")
public class CacheableUrlMappingServiceImpl implements UrlMappingService {

    private final UrlMappingService urlMappingService;
    private final JedisPool jedisPool;

    @Autowired
    public CacheableUrlMappingServiceImpl(@Qualifier("urlMappingServiceImpl") UrlMappingService urlMappingService) {
        this.urlMappingService = checkNotNull(urlMappingService);
        this.jedisPool = new JedisPool(new JedisPoolConfig(), GlobalConfig.getRedisHost(), GlobalConfig.getRedisPort());
    }

    @Override
    public UrlMapping getByLongUrl(LongUrl longUrl) {
        checkNotNull(longUrl);

        String key = "LongUrl:" + longUrl.getValue();
        Optional<String> cachedValue = tryGetCachedValueAndExtendExpiration(key);

        if (cachedValue.isPresent()) {
            return new UrlMapping(new ShortUrlPath(cachedValue.get()), longUrl);
        } else {
            UrlMapping urlMapping = urlMappingService.getByLongUrl(longUrl);
            setCachedValued(key, urlMapping.getShortUrlPath().getValue());
            return urlMapping;
        }
    }

    @Override
    public Optional<UrlMapping> getByShortUrlPath(ShortUrlPath shortUrlPath) {
        checkNotNull(shortUrlPath);

        String key = "ShortUrlPath:" + shortUrlPath.getValue();
        Optional<String> cachedValue = tryGetCachedValueAndExtendExpiration(key);

        if (cachedValue.isPresent()) {
            return Optional.of(new UrlMapping(shortUrlPath, new LongUrl(cachedValue.get())));
        } else {
            Optional<UrlMapping> urlMapping = urlMappingService.getByShortUrlPath(shortUrlPath);
            if (urlMapping.isPresent()) {
                setCachedValued(key, urlMapping.get().getLongUrl().getValue());
            }
            return urlMapping;
        }
    }

    private Optional<String> tryGetCachedValueAndExtendExpiration(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            Transaction t = jedis.multi();
            Response<String> cachedValue;
            cachedValue = t.get(key);
            t.expire(key, GlobalConfig.getExpireInSeconds());
            t.exec();

            if (cachedValue.get() == null) {
                return Optional.absent();
            } else {
                return Optional.of(cachedValue.get());
            }
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    private void setCachedValued(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            jedis.set(key, value, "NX", "EX", GlobalConfig.getExpireInSeconds());
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    @PreDestroy
    public void destroy() {
        jedisPool.destroy();
        System.out.println("Jedis is cleaned.");
    }
}
