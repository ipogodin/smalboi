package com.pogodin.shortener.service;

public interface LongShortStorage {

    boolean containsLongUrl(String longUrl);

    boolean containsShortKey(String shortKey);

    String getLongUrl(String shortKey);

    String getShortKey(String longUrl);

    void addLongToShortPair(String longUrl, String shortKey);

    String dump();
}
