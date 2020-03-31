package com.redhat.coolstore.Kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaCartProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    String kafkaTopic = "cart";

    public void send(String message) {
        System.out.println("sending cart msg");
        kafkaTemplate.send(kafkaTopic, message);
    }

}
