package com.densoft.reactiveelasticqueryservice.business;

import com.densoft.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryService {
    Flux<ElasticQueryServiceResponseModel> getDocumentByText(String text);
}
