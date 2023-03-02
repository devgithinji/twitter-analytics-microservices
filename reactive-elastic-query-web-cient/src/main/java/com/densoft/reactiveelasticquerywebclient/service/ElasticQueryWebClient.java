package com.densoft.reactiveelasticquerywebclient.service;

import com.densoft.elasticquerywebclientcommon.model.ElasticQueryWebClientRequestModel;
import com.densoft.elasticquerywebclientcommon.model.ElasticQueryWebClientResponseModel;
import reactor.core.publisher.Flux;

public interface ElasticQueryWebClient {
    Flux<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel request);
}
