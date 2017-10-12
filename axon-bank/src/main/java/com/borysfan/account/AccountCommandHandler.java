package com.borysfan.account;

import com.borysfan.Host;
import com.borysfan.core.api.CreateAccountCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountCommandHandler.class);

    private final Repository<Account> accountRepository;

    public AccountCommandHandler(Repository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @CommandHandler
    public void handle(final CreateAccountCommand createAccountCommand) throws Exception {
        LOGGER.info("CreateAccountCommand {}", Host.getName());
        accountRepository.newInstance(() -> new Account(createAccountCommand.getAccountId(), createAccountCommand.getOverdraftLimit()));
    }
}
