package com.infracloud.shorturl.controller;


import com.infracloud.shorturl.dto.UrlRequest;
import com.infracloud.shorturl.dto.UrlResponse;
import com.infracloud.shorturl.service.UrlShortnerService;
import com.infracloud.shorturl.service.UrlShortnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@RestController
@RequestMapping("/url")
class UrlShortenerController {
    private final UrlShortnerService urlShortnerService;

    @Autowired
    public UrlShortenerController(UrlShortnerServiceImpl service) {
        this.urlShortnerService = service;
    }

    @PostMapping("/shorten")
    public ResponseEntity<UrlResponse> shortenUrl(@RequestBody UrlRequest request){
        try {
            System.out.println("LongUrl:" + request.longUrl);
            UrlResponse urlDto = urlShortnerService.shortenUrl(request.longUrl);
            return ResponseEntity.ok(urlDto);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/getOriginalUrl/{shortUrl}")
    public ResponseEntity<String> getOriginal(@PathVariable String shortUrl){
        try{
            String originalUrl = urlShortnerService.getOriginalUrl(shortUrl);
            return ResponseEntity.status(HttpStatus.FOUND).body(originalUrl);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Short URL not found");
        }
    }

    @GetMapping("/{shortUrl}")
        public RedirectView redirectToOriginal(@PathVariable String shortUrl) {
            String originalUrl = urlShortnerService.getOriginalUrl(shortUrl);
            return new RedirectView(originalUrl);
        }

    @GetMapping("/top-domains")
    public ResponseEntity<List<String>> getTopRequestedDomains() {
        List<String> topDomains = urlShortnerService.getTopRequestedDomains();
        return ResponseEntity.ok(topDomains);
    }


}