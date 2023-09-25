package com.pogodin.shortener.service.impl;

import com.pogodin.shortener.service.CollectionPopulator;
import com.pogodin.shortener.service.KeyProvider;
import com.pogodin.shortener.service.LongShortStorage;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class KeyProviderImpl implements KeyProvider {
    // completely random number, should be changed in config depending on the load
    // TODO: consider dynamic change
    /**
     * responsible for the speed-ahead strings generation
     */
    public static final Integer DEFAULT_AHEAD = 128;
    public static final Integer SHORT_STRING_LEN = 6;

    public static final Integer LONGEST_ACCEPTED_TIME_MS = 250;

    @Autowired
    private LongShortStorage storage;

    @Autowired
    private CollectionPopulator collectionPopulator;

    private BlockingQueue<String> preparationQueue;


    @PostConstruct
    public void initQueue() {
        this.preparationQueue = new ArrayBlockingQueue<>(DEFAULT_AHEAD);
        collectionPopulator.populateQueue(preparationQueue, DEFAULT_AHEAD, storage);
    }

    @Override
    public String next() {
        int timeout = 100;
        while (timeout < LONGEST_ACCEPTED_TIME_MS) {
            try {
                if (preparationQueue.size() < DEFAULT_AHEAD / 2) {
                    collectionPopulator.populateQueue(preparationQueue, DEFAULT_AHEAD, storage);
                }
                return preparationQueue.poll(timeout, TimeUnit.MILLISECONDS);
            } catch (InterruptedException wer) {
                timeout += 50;
                // TODO: logger-warn: another attempt to the preparation queue
            }
        }
        throw new IllegalStateException("Unknown issue, key generator is dead");
    }




}
