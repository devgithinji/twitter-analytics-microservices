package com.densoft.elasticquery.webclient.service.impl;

import com.densoft.appconfigdata.config.ElasticQueryWebClientConfigData;
import com.densoft.elasticquery.webclient.service.ElasticQueryWebClient;
import com.densoft.elasticquerywebclientcommon.exception.ElasticQueryWebClientException;
import com.densoft.elasticquerywebclientcommon.model.ElasticQueryWebClientRequestModel;
import com.densoft.elasticquerywebclientcommon.model.ElasticQueryWebClientResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class TwitterElasticQueryWebClient implements ElasticQueryWebClient {
    private final WebClient.Builder webClientBuilder;

    private final ElasticQueryWebClientConfigData elasticQueryWebClientConfigData;

    public TwitterElasticQueryWebClient(@Qualifier("webClientBuilder") WebClient.Builder clientBuilder,
                                        ElasticQueryWebClientConfigData webClientConfigData) {
        this.webClientBuilder = clientBuilder;
        this.elasticQueryWebClientConfigData = webClientConfigData;
    }

    @Override
    public List<ElasticQueryWebClientResponseModel> getDataByText(ElasticQueryWebClientRequestModel requestModel) {
        log.info("Querying by text {}", requestModel.getText());
        return getWebClient(requestModel)
                .bodyToFlux(ElasticQueryWebClientResponseModel.class)
                .collectList()
                .block();
    }

    private WebClient.ResponseSpec getWebClient(ElasticQueryWebClientRequestModel requestModel) {
        return webClientBuilder
                .build()
                .method(HttpMethod.valueOf(elasticQueryWebClientConfigData.getQueryByText().getMethod()))
                .uri(elasticQueryWebClientConfigData.getQueryByText().getUri())
                .accept(MediaType.valueOf(elasticQueryWebClientConfigData.getQueryByText().getAccept()))
                .body(BodyInserters.fromPublisher(Mono.just(requestModel), createParameterizedTypeReference()))
                .retrieve()
                .onStatus(
                        httpStatus -> httpStatus.equals(HttpStatus.UNAUTHORIZED),
                        clientResponse -> Mono.just(new BadCredentialsException("Not authenticated!")))
                .onStatus(
                        HttpStatus::is4xxClientError,
                        cr -> Mono.just(new ElasticQueryWebClientException(cr.statusCode().getReasonPhrase())))
                .onStatus(
                        HttpStatus::is5xxServerError,
                        cr -> Mono.just(new Exception(cr.statusCode().getReasonPhrase())));
    }


    private <T> ParameterizedTypeReference<T> createParameterizedTypeReference() {
        return new ParameterizedTypeReference<>() {
        };
    }
}
