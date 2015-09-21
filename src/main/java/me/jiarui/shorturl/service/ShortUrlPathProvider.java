package me.jiarui.shorturl.service;


import me.jiarui.shorturl.entity.ShortUrlPath;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
public interface ShortUrlPathProvider {
    ShortUrlPath nextShortUrlPath();
}
