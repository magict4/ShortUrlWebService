package me.jiarui.shorturl.service;

import me.jiarui.shorturl.entity.ShortUrlPath;

public interface NumToShortUrlPathTransformer {
    ShortUrlPath transform(int num);
}
