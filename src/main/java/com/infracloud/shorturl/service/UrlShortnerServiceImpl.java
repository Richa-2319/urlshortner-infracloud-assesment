package com.infracloud.shorturl.service;

import com.infracloud.shorturl.constant.Constants;
import com.infracloud.shorturl.dto.UrlResponse;
import com.infracloud.shorturl.entity.UrlEntity;
import com.infracloud.shorturl.repository.UrlRepository;
import com.infracloud.shorturl.service.strategy.StrategyContext;
import org.springframework.stereotype.Service;

import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
            String host = uri.getHost();

            if(host == null || host.isBlank()) {
                throw new IllegalArgumentException("Invalid URL format");
            }

            String[] domainVerbs = host.split("\\.");
            StringBuilder sb = new StringBuilder();

            sb.append(domainVerbs[domainVerbs.length-2]);
            sb.append(".");
            sb.append(domainVerbs[domainVerbs.length-1]);

            return sb.toString();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
    }

    @Override
    public UrlResponse shortenUrl(String longUrl) {

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
        List<Object[]> finalresults = new ArrayList<>();
        int numberOfTopDomains = 3;
        Set<Long> values = new HashSet<>();
        for(Object[] objectList : results){
            values.add((Long) objectList[1]);
            if(values.size() <=3){
                finalresults.add(objectList);
            } else {
                break;
            }
        }
        return finalresults.stream()
                .map(result -> result[0] + " : " + result[1] )
                .collect(Collectors.toList());
    }
}
