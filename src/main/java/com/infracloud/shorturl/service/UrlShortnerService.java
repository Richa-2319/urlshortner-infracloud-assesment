package com.infracloud.shorturl.service;

import com.infracloud.shorturl.dto.UrlResponse;

import java.util.List;

public interface UrlShortnerService {
    UrlResponse shortenUrl(String longUrl);
    String getOriginalUrl(String shortUrl);
    List<String> getTopRequestedDomains();
}
