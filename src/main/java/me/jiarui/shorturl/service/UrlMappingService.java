package me.jiarui.shorturl.service;

import com.google.common.base.Optional;
import me.jiarui.shorturl.entity.LongUrl;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.entity.UrlMapping;


/**
 * Created by jerry.zhang on 7/6/2015.
 */
public interface UrlMappingService {
    UrlMapping getByLongUrl(LongUrl longUrl);

    Optional<UrlMapping> getByShortUrlPath(ShortUrlPath shortUrlPath);
}
