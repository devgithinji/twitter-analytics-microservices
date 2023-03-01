package com.densoft.elasticindex.service;

import com.densoft.elasticmodel.index.IndexModel;

import java.util.List;

public interface ElasticIndexClient<T extends IndexModel> {
    List<String> save(List<T> documents);
}
