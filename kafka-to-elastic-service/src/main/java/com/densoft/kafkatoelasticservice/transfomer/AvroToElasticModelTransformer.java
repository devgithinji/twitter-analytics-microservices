package com.densoft.kafkatoelasticservice.transfomer;

import com.densoft.demo.kafka.avro.model.TwitterAvroModel;
import com.densoft.elasticmodel.index.impl.TwitterIndexModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AvroToElasticModelTransformer {
    public List<TwitterIndexModel> getElasticModels(List<TwitterAvroModel> avroModels) {
        return avroModels.stream().map(avroModel -> TwitterIndexModel.builder()
                        .userId(avroModel.getId())
                        .id(String.valueOf(avroModel.getId()))
                        .text(String.valueOf(avroModel.getText()))
                        .createdAt(LocalDateTime.ofInstant(Instant.ofEpochMilli(avroModel.getCreatedAt()),
                                ZoneId.systemDefault())).build())
                .collect(Collectors.toList());
    }
}
