package com.infracloud.shorturl.service;

import com.infracloud.shorturl.dto.UrlResponse;

public interface UrlShortnerService {
    UrlResponse shortenUrl(String longUrl);
    String getOriginalUrl(String shortUrl);
}
