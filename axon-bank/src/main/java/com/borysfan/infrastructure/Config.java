package com.borysfan.infrastructure;

import com.mongodb.MongoClient;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.*;
import org.axonframework.commandhandling.gateway.IntervalRetryScheduler;
import org.axonframework.commandhandling.gateway.RetryScheduler;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.mongo.eventhandling.saga.repository.DefaultMongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.spring.eventsourcing.SpringAggregateSnapshotterFactoryBean;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestOperations;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@Configuration
public class Config {

    @Bean
    public SpringAggregateSnapshotterFactoryBean springAggregateSnapshotterFactoryBean() {
        return new SpringAggregateSnapshotterFactoryBean();
    }

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy(UnresolvedRoutingKeyPolicy.ERROR));
    }

    @Bean
    public CommandBusConnector springHttpCommandBusConnector(@Qualifier("localSegment") CommandBus localSegment,
                                                             RestOperations restOperations,
                                                             Serializer serialize) {
        return new SpringHttpCommandBusConnector(localSegment, restOperations, serialize);
    }

    @Bean
    @Primary
    public DistributedCommandBus springCloudDistributedCommandBus(CommandRouter commandRouter,
                                                                  CommandBusConnector commandBusConnector) {
        return new DistributedCommandBus(commandRouter, commandBusConnector);
    }

    @Bean("commandGateway")
    public CommandGatewayFactoryBean commandGateway(DistributedCommandBus springCloudDistributedCommandBus) {

        CommandGatewayFactoryBean commandGatewayFactoryBean = new CommandGatewayFactoryBean<>();

        //ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        //RetryScheduler retryScheduler = new IntervalRetryScheduler(scheduledThreadPool, 1, 1);
        //commandGatewayFactoryBean.setRetryScheduler(retryScheduler);
        commandGatewayFactoryBean.setCommandBus(springCloudDistributedCommandBus);
        return commandGatewayFactoryBean;
    }

    @Bean
    public SagaStore mongoSagaStore(MongoClient mongoClient, Serializer serializer) {
        return new MongoSagaStore(new DefaultMongoTemplate(mongoClient), serializer);
    }

}
