package com.infracloud.shorturl.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
public class UrlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String longUrl;
    private String shortUrl;
    private String domain;

    public String getLongUrl() {
        return longUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getDomain() {
        return domain;
    }

    public UrlEntity(String longUrl, String shortUrl, String domain) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
        this.domain = domain;
    }
}