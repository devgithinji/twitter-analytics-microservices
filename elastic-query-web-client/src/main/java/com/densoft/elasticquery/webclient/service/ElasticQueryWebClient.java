package com.densoft.elasticquery.webclient.service;

import com.densoft.elasticquery.webclient.model.ElasticQueryWebClientRequestModel;
import com.densoft.elasticquery.webclient.model.ElasticQueryWebClientResponseModel;

import java.util.List;

public interface ElasticQueryWebClient {
    List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel);
}
