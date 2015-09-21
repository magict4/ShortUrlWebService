package me.jiarui.shorturl.entity;


import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
public class UrlMapping {
    private final ShortUrlPath shortUrlPath;

    private final LongUrl longUrl;

    public UrlMapping(ShortUrlPath shortUrlPath, LongUrl longUrl) {
        this.shortUrlPath = checkNotNull(shortUrlPath);
        this.longUrl = checkNotNull(longUrl);
    }

    public ShortUrlPath getShortUrlPath() {
        return shortUrlPath;
    }

    public LongUrl getLongUrl() {
        return longUrl;
    }
}
