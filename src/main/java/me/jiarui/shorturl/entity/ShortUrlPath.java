package me.jiarui.shorturl.entity;

import me.jiarui.shorturl.common.GlobalConfig;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ShortUrlPath {
    private final String value;

    public ShortUrlPath(String value) {
        checkNotNull(value);

        // TODO: CHECK weather a valid url path
        checkArgument(value.length() <= GlobalConfig.getMaxShortUrlPathLength(), String.format("the value of url %s is too long", value));
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
