package com.pogodin.shortener.controller;

import com.pogodin.shortener.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class LongBoiController {

    private static final Logger logger = LoggerFactory.getLogger(LongBoiController.class);

    @Autowired
    private UrlShortenerService urlShortener;

    @GetMapping("/{shortKey}")
    public RedirectView redirect(@PathVariable String shortKey) {
        String longUrl = urlShortener.restoreUrl(shortKey);

        // If no long URL is found for the provided short URL, redirect to a 404 page or a custom error page.
        if (longUrl == null) {
            logger.warn("no string for " + shortKey + " found");
            urlShortener.dump();
            return new RedirectView("/error");
        }
        return new RedirectView(longUrl);
    }
}
