package com.pogodin.shortener.concept;

import com.pogodin.shortener.concept.exception.MapOverflowException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class LongShortUrlMap {
    private ConcurrentMap<String, String> longToShortKeyMap;
    private ConcurrentMap<String, String> shortToLongUrlMap;

    public LongShortUrlMap() {
        longToShortKeyMap = new ConcurrentHashMap<>();
        shortToLongUrlMap = new ConcurrentHashMap<>();
    }

    /**
     * placing the long url -> short url pair into the map
     * @param longUrl the long url
     * @param shortUrl the short url
     *
     */
    public void addShortUrl(String longUrl, String shortUrl) {
        if (longToShortKeyMap.containsKey(longUrl) || shortToLongUrlMap.containsKey(shortUrl)) {
            boolean longExists = longToShortKeyMap.containsKey(longUrl);
            throw new MapOverflowException((longExists ? "long url " + longUrl : "short key " + shortUrl)
                    + "cannot be placed into the LongShortUrlMap");
        }

        longToShortKeyMap.put(longUrl, shortUrl);
        shortToLongUrlMap.put(shortUrl, longUrl);
    }

    public String getLongUrl(String shortKey) {
        return shortToLongUrlMap.get(shortKey);
    }

    public String getShortKey(String longUrl) {
        return longToShortKeyMap.get(longUrl);
    }

    public String dump() {
        return longToShortKeyMap.entrySet() + " \n " + shortToLongUrlMap.entrySet();
    }
}
