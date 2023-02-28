package com.elasticonfig.config;

import com.densoft.appconfigdata.config.ElasticConfigData;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Configuration
@RequiredArgsConstructor
@EnableElasticsearchRepositories(basePackages = {
        "com.elasticindex.repository"
})
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {
    private final ElasticConfigData elasticConfigData;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        UriComponents uriComponents = UriComponentsBuilder.fromHttpUrl(elasticConfigData.getConnectionUrl()).build();
        return new RestHighLevelClient(RestClient.builder(new HttpHost(
                Objects.requireNonNull(uriComponents.getHost()),
                uriComponents.getPort(),
                uriComponents.getScheme())
        ).setRequestConfigCallback(builder ->
                builder.setConnectTimeout(elasticConfigData.getConnectionTimeOutMs())
                        .setSocketTimeout(elasticConfigData.getSocketTimeoutMs())
        ));
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }

}
