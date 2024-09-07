package com.infracloud.shorturl.service;

import com.infracloud.shorturl.constant.Constants;
import com.infracloud.shorturl.dto.UrlResponse;
import com.infracloud.shorturl.entity.UrlEntity;
import com.infracloud.shorturl.repository.UrlRepository;
import com.infracloud.shorturl.service.strategy.StrategyContext;
import org.springframework.stereotype.Service;

import java.net.*;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UrlShortnerServiceImpl implements UrlShortnerService{

    private final UrlRepository urlRepository;

    private final StrategyContext strategyContext;

    public UrlShortnerServiceImpl(UrlRepository urlRepository, StrategyContext strategyContext) {
        this.urlRepository = urlRepository;
        this.strategyContext = strategyContext;
    }

    private String extractDomain(String url) {
        try {
            URI uri = new URI(url);
            return uri.getHost();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
    }

    private boolean isValidUrl(String url) {
        // Implement URL validation logic
        String urlRegex = "^(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([-\\w \\./&%#=]*)*/?$";
        return url.matches(urlRegex);
    }

    @Override
    public UrlResponse shortenUrl(String longUrl) {

//        if (!isValidUrl(longUrl)) {
//            throw new IllegalArgumentException("Invalid URL format");
//        }

        String domain = extractDomain(longUrl);
        UrlEntity urlEntity = urlRepository.findByLongUrl(longUrl);
        if (urlEntity != null) {
            return UrlResponse.fromEntity(urlEntity);
        }

        String shortUrl = strategyContext.generateShortUrl(longUrl);
        urlEntity = new UrlEntity(longUrl, shortUrl, domain);
        urlRepository.save(urlEntity);

        return UrlResponse.fromEntity(urlEntity);
    }

    @Override
    public String getOriginalUrl(String shortUrl) {
        UrlEntity urlEntity = urlRepository.findByShortUrl(shortUrl);
        if (urlEntity == null) {
            throw new IllegalArgumentException("Invalid short URL");
        }
        return urlEntity.getLongUrl();
    }

    @Override
    public List<String> getTopRequestedDomains() {
        List<Object[]> results = urlRepository.findTopRequestedDomains();
        return results.stream()
                .limit(3) // Get top 3 domains
                .map(result -> result[0] + " (" + result[1] + ")")
                .collect(Collectors.toList());
    }
}
