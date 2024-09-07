package com.infracloud.shorturl.repository;

import com.infracloud.shorturl.entity.UrlEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UrlRepositoryTest {

        @Autowired
        private UrlRepository urlRepository;

        @Autowired
        private TestEntityManager testEntityManager;

        @Test
        void findByLongUrl_ExistingUrl_ReturnsUrlEntity() {
            // Given
            String longUrl = "http://example.com/some/long/url";
            String shortUrl = "abc123";
            UrlEntity urlEntity = new UrlEntity(longUrl, shortUrl, "example.com");
            testEntityManager.persist(urlEntity);

            // When
            UrlEntity result = urlRepository.findByLongUrl(longUrl);

            // Then
            assertNotNull(result);
            assertEquals(longUrl, result.getLongUrl());
            assertEquals(shortUrl, result.getShortUrl());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        void findByShortUrl_ExistingShortUrl_ReturnsUrlEntity() {
            // Given
            String longUrl = "http://example.com/some/long/url";
            String shortUrl = "abc123";
            UrlEntity urlEntity = new UrlEntity(longUrl, shortUrl, "example.com");
            testEntityManager.persist(urlEntity);

            // When
            UrlEntity result = urlRepository.findByShortUrl(shortUrl);

            // Then
            assertNotNull(result);
            assertEquals(longUrl, result.getLongUrl());
            assertEquals(shortUrl, result.getShortUrl());
            assertEquals("example.com", result.getDomain());
        }

        @Test
        void findTopRequestedDomains_ReturnsTopDomains() {
            // Given
            UrlEntity entity1 = new UrlEntity("http://example.com/url1", "short1", "example.com");
            UrlEntity entity2 = new UrlEntity("http://example.com/url2", "short2", "example.com");
            UrlEntity entity3 = new UrlEntity("http://anotherdomain.com/url1", "short3", "anotherdomain.com");
            testEntityManager.persist(entity1);
            testEntityManager.persist(entity2);
            testEntityManager.persist(entity3);

            // When
            List<Object[]> results = urlRepository.findTopRequestedDomains();

            // Then
            assertEquals(2, results.size());
            assertEquals("example.com", results.get(0)[0]);
            assertEquals(2L, results.get(0)[1]);
            assertEquals("anotherdomain.com", results.get(1)[0]);
            assertEquals(1L, results.get(1)[1]);
        }
    }
