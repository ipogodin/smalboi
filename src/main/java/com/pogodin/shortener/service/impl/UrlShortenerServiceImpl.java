package com.pogodin.shortener.service.impl;

import com.pogodin.shortener.service.KeyProvider;
import com.pogodin.shortener.service.LongShortStorage;
import com.pogodin.shortener.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UrlShortenerServiceImpl implements UrlShortenerService {

    private static final Logger logger = LoggerFactory.getLogger(UrlShortenerServiceImpl.class);

    @Autowired
    private LongShortStorage storage;

    @Autowired
    private KeyProvider keyProvider;

    @Override
    public String shortenUrl(String url) {
        if (!storage.containsLongUrl(url)) {
            String key = generateKey(url);
            storage.addLongToShortPair(url, key);
            logger.info("key = " + key);

        }
        return storage.getShortKey(url);
    }

    private String generateKey(String longUrl) {
        return keyProvider.next();
    }

    @Override
    public String restoreUrl(String shortKey) {
        return storage.getLongUrl(shortKey);
    }

    @Override
    public void dump() {
        logger.info(storage.dump());
    }
}
