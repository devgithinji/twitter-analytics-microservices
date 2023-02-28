package com.densoft.kafkatoelasticservice.consumer.impl;

import com.densoft.appconfigdata.config.KafkaConfigData;
import com.densoft.appconfigdata.config.KafkaConsumerConfigData;
import com.densoft.demo.kafka.avro.model.TwitterAvroModel;
import com.densoft.kafka.admin.clients.KafkaAdminClient;
import com.densoft.kafkatoelasticservice.consumer.KafkaConsumer;
import com.densoft.kafkatoelasticservice.transfomer.AvroToElasticModelTransformer;
import com.elasticindex.service.ElasticIndexClient;
import com.elasticmodel.index.impl.TwitterIndexModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaConsumer implements KafkaConsumer<Long, TwitterAvroModel> {

    private final KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;
    private final KafkaAdminClient kafkaAdminClient;
    private final KafkaConsumerConfigData kafkaConsumerConfigData;
    private final KafkaConfigData kafkaConfigData;
    private final AvroToElasticModelTransformer avroToElasticModelTransformer;
    private final ElasticIndexClient<TwitterIndexModel> elasticIndexClient;


    @EventListener
    public void onAppStarted(ApplicationStartedEvent applicationStartedEvent) {
        kafkaAdminClient.checkTopicsCreated();
        log.info("Topics wih name {} is ready for operations!", kafkaConfigData.getTopicNamesToCreate().toArray());
        Objects.requireNonNull(kafkaListenerEndpointRegistry.getListenerContainer(kafkaConsumerConfigData.getConsumerGroupId())).start();
    }

    @KafkaListener(id = "${kafka-consumer-config.consumer-group-id}", topics = "${kafka-config.topic-name}")
    @Override
    public void receive(
            @Payload List<TwitterAvroModel> messages,
            @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) List<Integer> keys,
            @Header(KafkaHeaders.RECEIVED_PARTITION_ID) List<Integer> partitions,
            @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of message received with keys {}, partitions {} and offsets {}, sending it to elastic: Thread id {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString(),
                Thread.currentThread().getId());
        List<TwitterIndexModel> twitterIndexModels = avroToElasticModelTransformer.getElasticModels(messages);
        List<String> documentIds = elasticIndexClient.save(twitterIndexModels);
        log.info("Documents saved to elastic search with ids {}", documentIds.toArray());

    }


}
