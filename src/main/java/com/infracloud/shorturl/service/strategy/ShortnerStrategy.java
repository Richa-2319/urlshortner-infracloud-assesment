package com.infracloud.shorturl.service.strategy;

public interface ShortnerStrategy {
    String generateShortUrl(String longUrl);
}
