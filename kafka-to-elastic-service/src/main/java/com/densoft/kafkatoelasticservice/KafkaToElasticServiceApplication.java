package com.densoft.kafkatoelasticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication(scanBasePackages = {"com.densoft"})
@EnableElasticsearchRepositories(basePackages = "com.densoft.elasticindex.repository")
public class KafkaToElasticServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaToElasticServiceApplication.class, args);
    }
}
