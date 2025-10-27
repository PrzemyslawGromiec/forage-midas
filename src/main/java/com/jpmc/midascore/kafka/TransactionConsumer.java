package com.jpmc.midascore.kafka;

import com.jpmc.midascore.foundation.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

import org.springframework.stereotype.Component;
import org.slf4j.Logger;

@Component
public class TransactionConsumer {
    private static final Logger log = LoggerFactory.getLogger(TransactionConsumer.class);

    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-core")
    public void onMessage(
            Transaction txn,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset
    ) {
        log.info("txn={} partition={} offset={}", txn, partition, offset);
    }
}
