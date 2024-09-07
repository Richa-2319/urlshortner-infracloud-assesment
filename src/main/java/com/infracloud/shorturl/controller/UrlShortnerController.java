package com.infracloud.shorturl.controller;


import com.infracloud.shorturl.service.UrlShortnerService;
import com.infracloud.shorturl.service.UrlShortnerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/url")
class UrlShortenerController {
    private final UrlShortnerService urlShortnerService;

    @Autowired
    public UrlShortenerController(UrlShortnerServiceImpl service) {
        this.urlShortnerService = service;
    }
}