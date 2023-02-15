package com.densoft.twittertokafkaservice.init.impl;

import com.densoft.appconfigdata.config.KafkaConfigData;
import com.densoft.kafka.admin.clients.KafkaAdminClient;
import com.densoft.twittertokafkaservice.init.StreamInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class KafkaStreamInitializer implements StreamInitializer {
    private final KafkaConfigData kafkaConfigData;
    private final KafkaAdminClient kafkaAdminClient;

    @Override
    public void init() {
        kafkaAdminClient.createTopics();
        kafkaAdminClient.checkSchemaRegistry();
        log.info("Topics with name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
    }
}
