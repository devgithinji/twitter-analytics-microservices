package com.densoft.twittertokafkaservice.transfomer;

import com.densoft.demo.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterStatusToAvroTransformer {
    public TwitterAvroModel getTwitterAvroModel(Status status) {
        return TwitterAvroModel.newBuilder()
                .setId(status.getId())
                .setUserId(status.getUser().getId())
                .setText(status.getText())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();
    }
}
