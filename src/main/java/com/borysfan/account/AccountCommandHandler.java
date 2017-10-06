package com.borysfan.account;

import com.borysfan.core.api.CreateAccountCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.Repository;
import org.springframework.stereotype.Component;

@Component
public class AccountCommandHandler {

    private final Repository<Account> accountRepository;

    public AccountCommandHandler(Repository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @CommandHandler
    public void handle(final CreateAccountCommand createAccountCommand) throws Exception {
        accountRepository.newInstance(() -> new Account(createAccountCommand.getAccountId(), createAccountCommand.getOverdraftLimit()));
    }
}
