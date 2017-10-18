package com.borysfan.mortage;

import com.borysfan.account.Account;
import org.axonframework.eventsourcing.AggregateFactory;
import org.axonframework.spring.eventsourcing.SpringPrototypeAggregateFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MortageConfig {

    @Bean
    @Scope("prototype")
    public Mortage mortage() {
        return new Mortage();
    }

    @Bean
    public AggregateFactory<Account> mortageAggregateFactory() {
        SpringPrototypeAggregateFactory<Account> aggregateFactory = new SpringPrototypeAggregateFactory<>();
        aggregateFactory.setPrototypeBeanName("mortage");
        return aggregateFactory;
    }
}
