package com.infracloud.shorturl.service.strategy;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class StrategyContext {
    private final ShortnerStrategy shortnerStrategy;

    public StrategyContext(@Qualifier("hash") ShortnerStrategy shortnerStrategy) {
        this.shortnerStrategy = shortnerStrategy;
    }

    public String generateShortUrl(String longUrl){
        return shortnerStrategy.generateShortUrl(longUrl);
    }
}
