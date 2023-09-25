package com.pogodin.shortener.controller;

import com.pogodin.shortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/urls")
public class ShortBoiController {

    @Autowired
    private UrlShortenerService urlShortener;

    @Value("${host.address}")
    private String currentHost;

    @GetMapping("/shorten")
    public String getShortenerUrl(@RequestParam(name = "url", defaultValue = "") String url,
                                  @RequestParam(name = "suggestion", defaultValue = "") String suggestion) {
        return urlShortener.shortenUrl(url);
    }

    @GetMapping("/unshorten")
    public String getUnshortenerUrl(@RequestParam(name = "key", defaultValue = "") String key) {
        return currentHost + urlShortener.restoreUrl(key);
    }
}
