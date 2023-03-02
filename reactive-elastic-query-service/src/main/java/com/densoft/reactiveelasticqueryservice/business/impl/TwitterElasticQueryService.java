package com.densoft.reactiveelasticqueryservice.business.impl;

import com.densoft.elasticmodel.index.impl.TwitterIndexModel;
import com.densoft.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import com.densoft.elasticqueryservicecommon.transformer.ElasticToResponseModelTransformer;
import com.densoft.reactiveelasticqueryservice.business.ElasticQueryService;
import com.densoft.reactiveelasticqueryservice.business.ReactiveElasticQueryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticQueryService implements ElasticQueryService {

    private final ReactiveElasticQueryClient<TwitterIndexModel> reactiveElasticQueryClient;
    private final ElasticToResponseModelTransformer elasticToResponseModelTransformer;

    @Override
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text) {
        log.info("Querying reactive elasticsearch for text {}", text);
        return reactiveElasticQueryClient.getIndexModelByText(text).map(elasticToResponseModelTransformer::getResponseModel);
    }
}
