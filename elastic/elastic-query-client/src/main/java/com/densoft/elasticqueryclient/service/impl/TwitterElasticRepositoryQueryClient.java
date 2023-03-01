package com.densoft.elasticqueryclient.service.impl;

import com.densoft.commonutil.util.CollectionsUtil;
import com.densoft.elasticmodel.index.impl.TwitterIndexModel;
import com.densoft.elasticqueryclient.repository.TwitterElasticsearchQueryRepository;
import com.densoft.elasticqueryclient.exception.ElasticQueryClientException;
import com.densoft.elasticqueryclient.service.ElasticQueryClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterElasticRepositoryQueryClient implements ElasticQueryClient<TwitterIndexModel> {

    private final TwitterElasticsearchQueryRepository twitterElasticsearchQueryRepository;

    @Override
    public TwitterIndexModel getIndexModelById(String id) {
        Optional<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findById(id);
        log.info("Document with id {} retrieved successfully", searchResult.orElseThrow(() -> new ElasticQueryClientException("No document found with id " + id)).getId());
        return searchResult.get();
    }

    @Override
    public List<TwitterIndexModel> getIndexModelByText(String text) {
        List<TwitterIndexModel> searchResult = twitterElasticsearchQueryRepository.findByText(text);
        log.info("{} of documents with text {} retrieved successfully", searchResult.size(), text);
        return searchResult;
    }

    @Override
    public List<TwitterIndexModel> getAllIndexModels() {
        List<TwitterIndexModel> searchResult = CollectionsUtil.getInstance().getListFromIterable(twitterElasticsearchQueryRepository.findAll());
        log.info("{} number of documents retrieved successfully", searchResult.size());
        return searchResult;
    }
}
