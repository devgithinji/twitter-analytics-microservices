package com.densoft.elasticqueryservice.api;

import com.densoft.elasticqueryservice.business.ElasticQueryService;
import com.densoft.elasticqueryservice.model.ElasticQueryServiceRequestModel;
import com.densoft.elasticqueryservice.model.ElasticQueryServiceResponseModel;
import com.densoft.elasticqueryservice.model.ElasticQueryServiceResponseModelV2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@RestController
@RequestMapping(value = "/documents", produces = "application/vnd.api.v1+json")
@Slf4j
@RequiredArgsConstructor
public class ElasticDocumentController {
    private final ElasticQueryService elasticQueryService;

    @Operation(summary = "Get all elastic documents")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {
                    @Content(mediaType = "application/vnd.api.v1+json",
                            schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getAllDocuments() {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getAllDocuments();
        log.info("Elasticsearch returned {} of documents", response.size());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all elastic document by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {
                    @Content(mediaType = "application/vnd.api.v1+json",
                            schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ElasticQueryServiceResponseModel> getDocumentById(@PathVariable("id") @NotEmpty String id) {
        ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);

        log.info("Elasticsearch returned document with id {}", id);
        return ResponseEntity.ok(elasticQueryServiceResponseModel);
    }

    @Operation(summary = "Get all elastic document by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {
                    @Content(mediaType = "application/vnd.api.v2+json",
                            schema = @Schema(implementation = ElasticQueryServiceResponseModelV2.class))
            }),
            @ApiResponse(responseCode = "400", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping(value = "/{id}", produces = "application/vnd.api.v2+json")
    public ResponseEntity<ElasticQueryServiceResponseModelV2> getDocumentByIdV2(@PathVariable("id") @NotEmpty String id) {
        ElasticQueryServiceResponseModel elasticQueryServiceResponseModel = elasticQueryService.getDocumentById(id);
        ElasticQueryServiceResponseModelV2 response = getV2Model(elasticQueryServiceResponseModel);
        log.info("Elasticsearch returned document with id {}", id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all elastic document by text")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful response", content = {
                    @Content(mediaType = "application/vnd.api.v1+json",
                            schema = @Schema(implementation = ElasticQueryServiceResponseModel.class))
            }),
            @ApiResponse(responseCode = "400", description = "Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/get-document-by-text")
    public ResponseEntity<List<ElasticQueryServiceResponseModel>> getDocumentByText(@RequestBody @Valid ElasticQueryServiceRequestModel elasticQueryServiceRequestModel) {
        List<ElasticQueryServiceResponseModel> response = elasticQueryService.getDocumentByText(elasticQueryServiceRequestModel.getText());
        log.info("Elasticsearch returned {} of documents", response.size());
        return ResponseEntity.ok(response);
    }

    private ElasticQueryServiceResponseModelV2 getV2Model(ElasticQueryServiceResponseModel elasticQueryServiceResponseModel) {
        ElasticQueryServiceResponseModelV2 responseModelV2 = ElasticQueryServiceResponseModelV2.builder()
                .id(Long.parseLong(elasticQueryServiceResponseModel.getId()))
                .userId(elasticQueryServiceResponseModel.getUserId())
                .text(elasticQueryServiceResponseModel.getText())
                .text2("Version 2 text")
                .build();
        responseModelV2.add(elasticQueryServiceResponseModel.getLinks());
        return responseModelV2;
    }


}
