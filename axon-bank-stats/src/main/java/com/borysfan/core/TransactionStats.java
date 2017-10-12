package com.borysfan.core;

import com.borysfan.core.api.MoneyDepositedEvent;
import com.borysfan.core.api.MoneyWithdrawnEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ProcessingGroup("transactionStats")
@Component
public class TransactionStats {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionStats.class);

    @EventHandler
    public void handle(MoneyWithdrawnEvent moneyWithdrawnEvent) {
        LOGGER.info("MoneyWithdrawnEvent: transactionId={} from account={} with amount={}", moneyWithdrawnEvent.getTransactionId(), moneyWithdrawnEvent.getAccountId().asString(), moneyWithdrawnEvent.getAmount().getValue());
    }

    @EventHandler
    public void handle(MoneyDepositedEvent moneyDepositedEvent) {
        LOGGER.info("MoneyDepositedEvent: transactionId={} to account={} with amount={}", moneyDepositedEvent.getTransactionId(), moneyDepositedEvent.getAccountId().asString(), moneyDepositedEvent.getAmount().getValue());
    }

}
