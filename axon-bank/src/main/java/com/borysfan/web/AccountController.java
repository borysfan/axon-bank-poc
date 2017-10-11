package com.borysfan.web;

import com.borysfan.Host;
import com.borysfan.account.Account;
import com.borysfan.core.AccountId;
import com.borysfan.core.OverdraftLimit;
import com.borysfan.core.api.CreateAccountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountController.class);

    private final AccountObjectRepository accountObjectRepository;
    private final Repository<Account> accountRepository;
    private final CommandGateway commandGateway;

    public AccountController(AccountObjectRepository accountObjectRepository, Repository<Account> accountRepository, CommandGateway commandGateway) {
        this.accountObjectRepository = accountObjectRepository;
        this.accountRepository = accountRepository;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void createNewAccount(@RequestBody NewAccountDto newAccountDto) throws UnknownHostException {
        LOGGER.info("REST createNewAccount {}", Host.getName());
        commandGateway.send(new CreateAccountCommand(new AccountId(newAccountDto.getAccountNumber()), new OverdraftLimit(newAccountDto.getOverdraft())));
    }

    @GetMapping("/{id}")
    public AccountObject details(@PathVariable("id") String id) {
        LOGGER.info("REST details {}", Host.getName());
        return accountObjectRepository.findOne(id);
    }


}
