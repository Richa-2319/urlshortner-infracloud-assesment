package com.infracloud.shorturl.dto;

import com.infracloud.shorturl.entity.UrlEntity;
import lombok.Getter;
import lombok.Setter;


public class UrlResponse {

    private String longUrl;
    private String shortUrl;

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public static UrlResponse fromEntity(UrlEntity entity){
        UrlResponse response = new UrlResponse();
        response.setLongUrl(entity.getLongUrl());
        response.setShortUrl(entity.getShortUrl());
        return response;
    }
}