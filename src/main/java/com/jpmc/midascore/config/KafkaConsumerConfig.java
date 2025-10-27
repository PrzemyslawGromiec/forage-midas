// NOTE: This configuration class is commented out because the project
// currently uses YAML-based Kafka setup. Left here for reference if
// manual bean configuration is needed in the future.

//package com.jpmc.midascore.config;
//
//import jakarta.annotation.PostConstruct;
//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.StringDeserializer;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
//import org.springframework.kafka.support.serializer.JsonDeserializer;
//import com.jpmc.midascore.foundation.Transaction;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.annotation.EnableKafka;
//import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@EnableKafka
//@Configuration
//public class KafkaConsumerConfig {
//
//    @Value("${spring.kafka.bootstrap-servers:${spring.embedded.kafka.brokers}}")
//    private String bootstrapServers;
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Transaction> kafkaListenerContainerFactory() {
//        JsonDeserializer<Transaction> deserializer = new JsonDeserializer<>(Transaction.class, false);
//        deserializer.addTrustedPackages("com.jpmc.midascore.foundation");
//
//        Map<String, Object> props = new HashMap<>();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG, "midas-core");
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        var consumerFactory = new DefaultKafkaConsumerFactory<>(
//                props, new StringDeserializer(), deserializer);
//
//        var factory = new ConcurrentKafkaListenerContainerFactory<String, Transaction>();
//        factory.setConsumerFactory(consumerFactory);
//        return factory;
//    }
//
//    @PostConstruct
//    void logBroker() {
//        System.out.println("Kafka broker: " + bootstrapServers);
//    }
//
//}
