package com.borysfan.account;

import com.mongodb.MongoClient;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.eventsourcing.EventCountSnapshotTriggerDefinition;
import org.axonframework.eventsourcing.EventSourcingRepository;
import org.axonframework.eventsourcing.Snapshotter;
import org.axonframework.eventsourcing.eventstore.EventStorageEngine;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.mongo.eventsourcing.eventstore.DefaultMongoTemplate;
import org.axonframework.mongo.eventsourcing.eventstore.MongoEventStorageEngine;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AccountConfiguration {

    @Autowired
    private EventStore eventStore;

    @Autowired
    private Snapshotter snapshotter;

    @Bean
    public EventStorageEngine eventStore(MongoClient mongoClient) {
        return new MongoEventStorageEngine(new DefaultMongoTemplate(mongoClient));
    }

    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("BankEvents").build();
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable("BankEvents").build();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(queue()).to(exchange()).with("*").noargs();
    }

    @Autowired
    public void configure(AmqpAdmin amqpAdmin) {
        amqpAdmin.declareExchange(exchange());
        amqpAdmin.declareQueue(queue());
        amqpAdmin.declareBinding(binding());
    }

    @Bean
    @Scope("prototype")
    public Account account() {
        return new Account();
    }

    @Bean
    public AggregateFactory<Account> accountAggregateFactory() {
        SpringPrototypeAggregateFactory<Account> aggregateFactory = new SpringPrototypeAggregateFactory<>();
        aggregateFactory.setPrototypeBeanName("account");
        return aggregateFactory;
    }

    @Bean
    public Repository<Account> accountRepository() {
        EventCountSnapshotTriggerDefinition snapshotTriggerDefinition = new EventCountSnapshotTriggerDefinition(snapshotter, 5);

        return new EventSourcingRepository<Account>(
                accountAggregateFactory(),
                eventStore,
                snapshotTriggerDefinition
        );
    }
}
