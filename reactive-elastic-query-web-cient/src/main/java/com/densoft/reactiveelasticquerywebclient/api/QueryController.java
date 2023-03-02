package com.densoft.reactiveelasticquerywebclient.api;

import com.densoft.elasticquerywebclientcommon.model.ElasticQueryWebClientRequestModel;
import com.densoft.elasticquerywebclientcommon.model.ElasticQueryWebClientResponseModel;
import com.densoft.reactiveelasticquerywebclient.service.ElasticQueryWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.spring5.context.webflux.IReactiveDataDriverContextVariable;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class QueryController {
    private final ElasticQueryWebClient elasticQueryWebClient;

    @GetMapping
    public String index() {
        return "index";
    }

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("elasticQueryClientRequestModel", ElasticQueryWebClientRequestModel.builder().build());
        return "home";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @PostMapping("/query-by-text")
    public String queryByText(@Valid ElasticQueryWebClientRequestModel requestModel, Model model) {
        Flux<ElasticQueryWebClientResponseModel> responseModelFlux = elasticQueryWebClient.getDataByText(requestModel);
        responseModelFlux = responseModelFlux.log();
        IReactiveDataDriverContextVariable reactiveData = new ReactiveDataDriverContextVariable(responseModelFlux, 1);
        model.addAttribute("elasticQueryClientResponseModels", reactiveData);
        model.addAttribute("searchText", requestModel.getText());
        model.addAttribute("elasticQueryClientRequestModel", ElasticQueryWebClientRequestModel.builder().build());
        log.info("Returning from reactive client controller for text {}", requestModel.getText());
        return "home";
    }
}
