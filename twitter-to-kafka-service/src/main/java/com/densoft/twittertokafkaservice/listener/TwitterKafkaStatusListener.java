package com.densoft.twittertokafkaservice.listener;

import com.densoft.appconfigdata.config.KafkaConfigData;
import com.densoft.demo.kafka.avro.model.TwitterAvroModel;
import com.densoft.kafka.producer.service.KafkaProducer;
import com.densoft.twittertokafkaservice.transfomer.TwitterStatusToAvroTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.StatusAdapter;

@Component
@Slf4j
@RequiredArgsConstructor
public class TwitterKafkaStatusListener extends StatusAdapter {
    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducer<Long, TwitterAvroModel> kafkaProducer;
    private final TwitterStatusToAvroTransformer twitterStatusToAvroTransformer;

    @Override
    public void onStatus(Status status) {
        log.info("Received status with text {} and topic name {}", status.getText(), kafkaConfigData.getTopicName());
        TwitterAvroModel twitterAvroModel = twitterStatusToAvroTransformer.getTwitterAvroModel(status);
        kafkaProducer.send(kafkaConfigData.getTopicName(), twitterAvroModel.getUserId(), twitterAvroModel);
    }
}
