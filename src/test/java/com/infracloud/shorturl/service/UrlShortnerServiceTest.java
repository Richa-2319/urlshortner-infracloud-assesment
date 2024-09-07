package com.infracloud.shorturl.service;

import com.infracloud.shorturl.dto.UrlResponse;
import com.infracloud.shorturl.entity.UrlEntity;
import com.infracloud.shorturl.repository.UrlRepository;
import com.infracloud.shorturl.service.strategy.StrategyContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UrlShortnerServiceTest {
    @Mock
    private UrlRepository urlRepository;

    @Mock
    private StrategyContext strategyContext;

    @InjectMocks
    private UrlShortnerServiceImpl urlShortnerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shortenUrl_WhenUrlExists_ReturnsExistingUrlResponse() {
        // Given
        String longUrl = "http://example.com/some/long/url";
        String shortUrl = "abc123";
        UrlEntity existingUrlEntity = new UrlEntity(longUrl, shortUrl, "example.com");
        when(urlRepository.findByLongUrl(longUrl)).thenReturn(existingUrlEntity);

        // When
        UrlResponse response = urlShortnerService.shortenUrl(longUrl);

        // Then
        assertNotNull(response);
        assertEquals(longUrl, response.getLongUrl());
        assertEquals(shortUrl, response.getShortUrl());
        verify(urlRepository, times(1)).findByLongUrl(longUrl);
        verify(urlRepository, never()).save(any());
    }

    @Test
    public void shortenUrl_WhenUrlDoesNotExist_CreatesNewUrlResponse() {
        // Given
        String longUrl = "http://example.com/some/long/url";
        String shortUrl = "abc123";
        when(urlRepository.findByLongUrl(longUrl)).thenReturn(null);
        when(strategyContext.generateShortUrl(longUrl)).thenReturn(shortUrl);

        // When
        UrlResponse response = urlShortnerService.shortenUrl(longUrl);

        // Then
        assertNotNull(response);
        assertEquals(longUrl, response.getLongUrl());
        assertEquals(shortUrl, response.getShortUrl());
        verify(urlRepository, times(1)).findByLongUrl(longUrl);
        verify(urlRepository, times(1)).save(any(UrlEntity.class));
    }

    @Test
    public void getOriginalUrl_WhenShortUrlExists_ReturnsOriginalUrl() {
        // Given
        String shortUrl = "abc123";
        String longUrl = "http://example.com/some/long/url";
        UrlEntity urlEntity = new UrlEntity(longUrl, shortUrl, "example.com");
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(urlEntity);

        // When
        String result = urlShortnerService.getOriginalUrl(shortUrl);

        // Then
        assertEquals(longUrl, result);
        verify(urlRepository, times(1)).findByShortUrl(shortUrl);
    }

    @Test
    public void getOriginalUrl_WhenShortUrlDoesNotExist_ThrowsException() {
        // Given
        String shortUrl = "invalidShortUrl";
        when(urlRepository.findByShortUrl(shortUrl)).thenReturn(null);

        // When & Then
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            urlShortnerService.getOriginalUrl(shortUrl);
        });

        assertEquals("Invalid short URL", exception.getMessage());
        verify(urlRepository, times(1)).findByShortUrl(shortUrl);
    }

    @Test
    public void getTopRequestedDomains_ReturnsTopDomains() {
        // Given
        List<Object[]> results = Arrays.asList(
                new Object[]{"example.com", 5},
                new Object[]{"anotherdomain.com", 3},
                new Object[]{"yetanother.com", 2}
        );
        when(urlRepository.findTopRequestedDomains()).thenReturn(results);

        // When
        List<String> topDomains = urlShortnerService.getTopRequestedDomains();

        // Then
        assertEquals(3, topDomains.size());
        assertEquals("example.com (5)", topDomains.get(0));
        assertEquals("anotherdomain.com (3)", topDomains.get(1));
        assertEquals("yetanother.com (2)", topDomains.get(2));
        verify(urlRepository, times(1)).findTopRequestedDomains();
    }
}
