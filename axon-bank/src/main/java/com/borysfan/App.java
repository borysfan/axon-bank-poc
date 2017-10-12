package com.borysfan;

import com.borysfan.transaction.MoneyTransactionSaga;
import com.mongodb.MongoClient;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.commandhandling.distributed.*;
import org.axonframework.config.EventHandlingConfiguration;
import org.axonframework.eventhandling.saga.repository.SagaStore;
import org.axonframework.mongo.eventhandling.saga.repository.DefaultMongoTemplate;
import org.axonframework.mongo.eventhandling.saga.repository.MongoSagaStore;
import org.axonframework.mongo.eventhandling.saga.repository.MongoTemplate;
import org.axonframework.serialization.Serializer;
import org.axonframework.spring.commandhandling.gateway.CommandGatewayFactoryBean;
import org.axonframework.springcloud.commandhandling.SpringCloudCommandRouter;
import org.axonframework.springcloud.commandhandling.SpringHttpCommandBusConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestOperations;

@EnableDiscoveryClient
@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @GetMapping("/name")
    public String name() {
        return "axon-bank";
    }

    @Bean
    public CommandRouter springCloudCommandRouter(DiscoveryClient discoveryClient) {
        return new SpringCloudCommandRouter(discoveryClient, new AnnotationRoutingStrategy(UnresolvedRoutingKeyPolicy.RANDOM_KEY));
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
        commandGatewayFactoryBean.setCommandBus(springCloudDistributedCommandBus);
        return commandGatewayFactoryBean;
    }

    @Bean
    public SagaStore mongoSagaStore(MongoClient mongoClient, Serializer serializer) {
        return new MongoSagaStore(new DefaultMongoTemplate(mongoClient), serializer);
    }

    @Autowired
    public void configure(EventHandlingConfiguration config) {


    }

}
