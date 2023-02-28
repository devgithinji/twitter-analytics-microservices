package com.densoft.kafkatoelasticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
        "com.densoft.appconfigdata",
        "com.densoft.kafkatoelasticservice",
        "com.densoft.kafkaconsumer",
        "com.densoft.kafka.admin",
        "com.densoft.retryConfig",
        "com.elasticindex",
        "com.elasticonfig",
        "com.elasticmodel"
})
public class KafkaToElasticServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaToElasticServiceApplication.class, args);
    }
}
