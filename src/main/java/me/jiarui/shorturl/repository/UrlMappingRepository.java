package me.jiarui.shorturl.repository;

import com.google.common.base.Optional;
import me.jiarui.shorturl.entity.LongUrl;
import me.jiarui.shorturl.entity.ShortUrlPath;
import me.jiarui.shorturl.entity.UrlMapping;

public interface UrlMappingRepository {
    Optional<UrlMapping> getByLongUrl(LongUrl longUrl);

    Optional<UrlMapping> getByShortUrlPath(ShortUrlPath shortUrlPath);

    void add(UrlMapping urlMapping);
}
