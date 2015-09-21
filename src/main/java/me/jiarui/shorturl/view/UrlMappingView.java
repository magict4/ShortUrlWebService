package me.jiarui.shorturl.view;

/**
 * Created by Sher10ck on 7/11/15.
 */
public class UrlMappingView {
    private String longUrl;

    private String shortUrl;

    @SuppressWarnings("unused")
    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    @SuppressWarnings("unused")
    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }
}
