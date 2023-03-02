package com.densoft.reactiveelasticqueryservice.api;

import com.densoft.elasticqueryservicecommon.model.ElasticQueryServiceRequestModel;
import com.densoft.elasticqueryservicecommon.model.ElasticQueryServiceResponseModel;
import com.densoft.reactiveelasticqueryservice.business.ElasticQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@RestController
@RequestMapping("/documents")
@Slf4j
@RequiredArgsConstructor
public class ElasticDocumentController {
    private final ElasticQueryService elasticQueryService;

    @PostMapping(value = "/get-doc-by-text", produces = MediaType.TEXT_EVENT_STREAM_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Flux<ElasticQueryServiceResponseModel> getDocumentByText(@RequestBody @Valid ElasticQueryServiceRequestModel requestModel) {
        Flux<ElasticQueryServiceResponseModel> responseModelFlux = elasticQueryService.getDocumentByText(requestModel.getText());
        responseModelFlux = responseModelFlux.log();
        log.info("Returning from query reactive service for text {}!", requestModel.getText());
        return responseModelFlux;
    }
}
