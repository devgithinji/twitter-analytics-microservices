package com.densoft.elasticqueryservice.api;

import com.densoft.elasticqueryservice.business.ElasticQueryService;
import com.densoft.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.densoft.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import com.densoft.elasticqueryservice.model.ElasticQueryServiceResponseModelV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping("/documents")
@Slf4j
@RequiredArgsConstructor
public class ElasticDocumentController {
    private final ElasticQueryService elasticQueryService;

    @GetMapping("/v1")
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
        log.info("Elasticsearch returned {} of documents", response.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable("id") @NotEmpty String id) {
        ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);

        log.info("Elasticsearch returned document with id {}", id);
        return ResponseEntity.ok(elasticQueryServiceResponseModel);
    }

    @GetMapping("/v2/{id}")
    public ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentByIdV2(@PathVariable("id") @NotEmpty String id) {
        ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
        log.info("Elasticsearch returned document with id {}", id);
        ElasticQueryServiceResponseModelV2 responseModelV2 = getV2Model(elasticQueryServiceResponseModel);
        return ResponseEntity.ok(responseModelV2);
    }

    @PostMapping("/v1/get-document-by-text")
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(@RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText());
        log.info("Elasticsearch returned {} of documents", response.size());
        return ResponseEntity.ok(response);
    }

    private ElasticQueryServiceResponseModelV2 getV2Model(ElasticQueryServiceResponseModel elasticQueryServiceResponseModel) {
        ElasticQueryServiceResponseModelV2 responseModelV2 = ElasticQueryServiceResponseModelV2.builder()
                .id(Long.parseLong(elasticQueryServiceResponseModel.getId()))
                .text(elasticQueryServiceResponseModel.getText())
                .userId(elasticQueryServiceResponseModel.getUserId())
                .createdAt(elasticQueryServiceResponseModel.getCreatedAt())
                .build();
        responseModelV2.add(elasticQueryServiceResponseModel.getLinks());
        return responseModelV2;
    }


}
