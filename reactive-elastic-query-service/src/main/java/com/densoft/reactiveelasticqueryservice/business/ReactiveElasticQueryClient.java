package com.densoft.reactiveelasticqueryservice.business;

import com.densoft.elasticmodel.index.IndexModel;
import com.densoft.elasticmodel.index.impl.TwitterIndexModel;
import reactor.core.publisher.Flux;

public interface ReactiveElasticQueryClient<T extends IndexModel> {
    Flux<TwitterIndexModel> getIndexModelByText(String text);
}
