package me.jiarui.shorturl.dto;

public class UrlMappingDto {
    private String shortUrlPath;

    private String longUrl;

    public String getShortUrlPath() {
        return shortUrlPath;
    }

    public void setShortUrlPath(String shortUrlPath) {
        this.shortUrlPath = shortUrlPath;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

}

