package com.borysfan.reply;

import com.borysfan.core.api.MoneyDepositedEvent;
import com.borysfan.core.api.MoneyWithdrawnEvent;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@ProcessingGroup("echo")
@Component
public class EchoHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(EventHandler.class);

    @EventHandler
    public void on (MoneyDepositedEvent event) {
        LOGGER.info("Money deposited {}", event.getAmount());
    }

    @EventHandler
    public void on(MoneyWithdrawnEvent event) {
        LOGGER.info("Money withdrawn {}", event.getAmount());
    }
}
