package com.densoft.reactiveelasticqueryservice.business.impl;

import com.densoft.appconfigdata.config.ElasticQueryServiceConfigData;
import com.densoft.elasticmodel.index.impl.TwitterIndexModel;
import com.densoft.reactiveelasticqueryservice.business.ReactiveElasticQueryClient;
import com.densoft.reactiveelasticqueryservice.repository.ElasticQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterReactiveElasticQueryClient implements ReactiveElasticQueryClient<TwitterIndexModel> {
    private final ElasticQueryRepository elasticQueryRepository;
    private final ElasticQueryServiceConfigData elasticQueryServiceConfigData;

    @Override
    public Flux<TwitterIndexModel> getIndexModelByText(String text) {
        log.info("Getting data from elastic search for text {}", text);
        return elasticQueryRepository
                .findByText(text)
                .delayElements(Duration.ofMillis(elasticQueryServiceConfigData.getBackPressureDelayMs()));
    }
}
