package com.infracloud.shorturl.dto;

import com.infracloud.shorturl.constant.Constants;
import com.infracloud.shorturl.entity.UrlEntity;
import lombok.AllArgsConstructor;



public class UrlResponse {

    private String longUrl;
    private String shortUrl;

    public UrlResponse() {
    }

    public UrlResponse(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public static UrlResponse fromEntity(UrlEntity entity){
        UrlResponse response = new UrlResponse();
        response.setLongUrl(entity.getLongUrl());
        response.setShortUrl(Constants.BASEAPPDOMAIN + entity.getShortUrl());
        return response;
    }
}