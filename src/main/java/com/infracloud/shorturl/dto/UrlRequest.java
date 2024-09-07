package com.infracloud.shorturl.dto;

import lombok.Getter;
import lombok.Setter;


public class UrlRequest {
    public String longUrl;

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }
}
