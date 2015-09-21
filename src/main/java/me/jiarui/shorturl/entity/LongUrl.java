package me.jiarui.shorturl.entity;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by jerry.zhang on 7/6/2015.
 */
public class LongUrl {
    private final String value;

    public LongUrl(String url) {
        checkNotNull(url);
        //TODO: Need to figure out a way to validate the input url
        this.value = url;
    }

    public String getValue() {
        return value;
    }
}
