package com.densoft.elasticqueryservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"com.densoft"})
@EnableElasticsearchRepositories(basePackages = {
        "com.densoft.elasticqueryclient.repository",
        "com.densoft.elasticindex.repository"
})
public class ElasticQueryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticQueryServiceApplication.class, args);
    }
}
