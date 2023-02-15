package com.densoft.twittertokafkaservice.runner.impl;


import com.densoft.appconfigdata.config.TwitterToKafkaServiceConfig;
import com.densoft.twittertokafkaservice.listener.TwitterKafkaStatusListener;
import com.densoft.twittertokafkaservice.runner.StreamRunner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
@Slf4j
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "false", matchIfMissing = true)
public class TwitterKafkaStreamRunner implements StreamRunner {

    private final TwitterToKafkaServiceConfig twitterToKafkaServiceConfig;
    private final TwitterKafkaStatusListener twitterKafkaStatusListener;
    private TwitterStream twitterStream;

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterKafkaStatusListener);
        addFilter();
    }

    @PreDestroy
    public void shutDown() {
        if (twitterStream != null) {
            log.info("closing twitter stream!");
            twitterStream.shutdown();
        }
    }

    private void addFilter() {
        String[] keyWords = twitterToKafkaServiceConfig.getTwitterKeywords().toArray(new String[0]);
        FilterQuery filterQuery = new FilterQuery(keyWords);
        twitterStream.filter(filterQuery);
        log.info("Started filtering twitter stream for keywords {}", Arrays.toString(keyWords));
    }
}
