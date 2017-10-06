package com.borysfan.web;

import com.borysfan.account.Account;
import com.borysfan.core.AccountId;
import com.borysfan.core.OverdraftLimit;
import com.borysfan.core.api.CreateAccountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.commandhandling.model.Aggregate;
import org.axonframework.commandhandling.model.Repository;
import org.axonframework.messaging.unitofwork.DefaultUnitOfWork;
import org.axonframework.messaging.unitofwork.UnitOfWork;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountObjectRepository accountObjectRepository;
    private final Repository<Account> accountRepository;
    private final CommandGateway commandGateway;

    public AccountController(AccountObjectRepository accountObjectRepository, Repository<Account> accountRepository, CommandGateway commandGateway) {
        this.accountObjectRepository = accountObjectRepository;
        this.accountRepository = accountRepository;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void createNewAccount(@RequestBody NewAccountDto newAccountDto) {
        commandGateway.send(new CreateAccountCommand(new AccountId(newAccountDto.getAccountNumber()), new OverdraftLimit(newAccountDto.getOverdraft())));
    }

    @GetMapping("/{id}")
    public AccountObject details(@PathVariable("id") String id) {
        //Aggregate<Account> aggregate = accountRepository.load(id);
        return accountObjectRepository.findOne(id);
    }


}
