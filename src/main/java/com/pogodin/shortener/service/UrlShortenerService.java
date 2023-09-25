package com.pogodin.shortener.service;

public interface UrlShortenerService {

    String shortenUrl(String url);

    String restoreUrl(String shortenedId);

    void dump();
}
