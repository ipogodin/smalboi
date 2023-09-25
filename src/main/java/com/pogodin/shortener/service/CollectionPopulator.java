package com.pogodin.shortener.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

@Service
public class CollectionPopulator {

    private static final Logger logger = LoggerFactory.getLogger(CollectionPopulator.class);

    private static final Integer SHORT_STRING_LEN = 6;
    private static final Integer MAX_TIMEOUT = 250;
    private static final Long RANDOM_SEED = 12412049L;

    // TODO: alternative random
    private final Random random;

    public CollectionPopulator() {
        random = new Random(RANDOM_SEED);
    }

    @Async
    public void populateQueue(BlockingQueue<String> preparationQueue, int minSize,
                              LongShortStorage existentStorage) {
        char[] tempString = new char[SHORT_STRING_LEN];

        while(preparationQueue.size() < minSize) {
            for (int i = 0; i < tempString.length; i++) {
                int nextLetter = random.nextInt('z' - 'a');
                tempString[i] = (char)('a' + nextLetter);
            }
            final String newShortString = new String(tempString);
            if (!existentStorage.containsShortKey(newShortString)) {
                    try {
                        preparationQueue.offer(newShortString, MAX_TIMEOUT, TimeUnit.MILLISECONDS);
                    } catch (InterruptedException e) {
                        logger.warn("interruption exception on attampt to offer to a queue" + e);
                        // TODO : log warn: issue adding element into concurrent queue
                    }
            }
        }
    }
}
