package com.elasticindex.repository;

import com.elasticmodel.index.impl.TwitterIndexModel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TwitterElasticSearchIndexRepository extends ElasticsearchRepository<TwitterIndexModel, String> {
}
