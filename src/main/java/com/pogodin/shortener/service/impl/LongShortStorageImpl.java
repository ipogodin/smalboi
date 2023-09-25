package com.pogodin.shortener.service.impl;

import com.pogodin.shortener.concept.LongShortUrlMap;
import com.pogodin.shortener.concept.exception.MapOverflowException;
import com.pogodin.shortener.service.LongShortStorage;
import org.springframework.stereotype.Service;

@Service
public class LongShortStorageImpl implements LongShortStorage {

    LongShortUrlMap storage;

    public LongShortStorageImpl() {
        storage = new LongShortUrlMap();
    }

    @Override
    public boolean containsLongUrl(String longUrl) {
        return storage.getShortKey(longUrl) != null;
    }

    @Override
    public boolean containsShortKey(String shortKey) {
        return storage.getLongUrl(shortKey) != null;
    }

    @Override
    public String getLongUrl(String shortKey) {
        return storage.getLongUrl(shortKey);
    }

    @Override
    public String getShortKey(String longUrl) {
        return storage.getShortKey(longUrl);
    }

    /**
     *
     * @param longUrl
     * @param shortKey
     * @throws MapOverflowException, runtime exception in case if longUrl or shortKey already exists
     */
    @Override
    public void addLongToShortPair(String longUrl, String shortKey) {
        storage.addShortUrl(longUrl, shortKey);
    }

    @Override
    public String dump() {
        return storage.dump();
    }
}
