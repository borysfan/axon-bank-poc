package com.borysfan.account;

import com.mongodb.MongoClient;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AccountConfiguration {

    @Bean
    public EventStorageEngine eventStore(MongoClient mongoClient) {
        return new MongoEventStorageEngine(new DefaultMongoTemplate(mongoClient));
    }
}
