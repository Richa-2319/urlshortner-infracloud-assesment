package com.infracloud.shorturl.repository;

import com.infracloud.shorturl.entity.UrlEntity;

public interface UrlRepository {
    UrlEntity findByLongUrl(String longUrl);
    UrlEntity findByShortUrl(String shortUrl);
}
