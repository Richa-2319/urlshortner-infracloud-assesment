package com.infracloud.shorturl.repository;

import com.infracloud.shorturl.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UrlRepository extends JpaRepository<UrlEntity,Long> {
    UrlEntity findByLongUrl(String longUrl);
    UrlEntity findByShortUrl(String shortUrl);

    @Query("SELECT u.domain, COUNT(u.domain) as count FROM UrlEntity u " +
            "GROUP BY u.domain ORDER BY count DESC")
    List<Object[]> findTopRequestedDomains();
}
