package com.densoft.kafkatoelasticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {
        "com.densoft.appconfigdata",
        "com.densoft.kafkatoelasticservice",
        "com.densoft.kafkaconsumer",
        "com.densoft.kafka.admin",
        "com.densoft.retryConfig",
})
public class KafkaToElasticServiceApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(KafkaToElasticServiceApplication.class, args);
    }
}
