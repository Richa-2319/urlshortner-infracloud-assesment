package com.infracloud.shorturl.service.strategy;

import com.infracloud.shorturl.constant.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class HashStrategyTest {

    @Autowired
    private HashStrategy hashStrategy;

    @Test
    void generateShortUrl_ShouldReturnShortUrlOfLength8() {
        // Given
        String longUrl = "http://example.com/some/long/url";

        // When
        String shortUrl = hashStrategy.generateShortUrl(longUrl);

        // Then
        assertEquals(8, shortUrl.length(), "Short URL should be of length 8");
        for (char ch : shortUrl.toCharArray()) {
            assertTrue(Constants.BASE62CHARS.indexOf(ch) != -1, "Short URL should contain valid Base62 characters");
        }
    }

    @Test
    void generateShortUrl_ShouldReturnDifferentShortUrlsForDifferentInputs() {
        // Given
        String longUrl1 = "http://example.com/some/long/url1";
        String longUrl2 = "http://example.com/some/long/url2";

        // When
        String shortUrl1 = hashStrategy.generateShortUrl(longUrl1);
        String shortUrl2 = hashStrategy.generateShortUrl(longUrl2);

        // Then
        assertNotEquals(shortUrl1, shortUrl2, "Short URLs should be different for different long URLs");
    }
}
