package com.borysfan.web;

import com.borysfan.core.AccountId;
import com.borysfan.core.OverdraftLimit;
import com.borysfan.core.api.CreateAccountCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountObjectRepository accountObjectRepository;
    private final CommandGateway commandGateway;

    public AccountController(AccountObjectRepository accountObjectRepository, CommandGateway commandGateway) {
        this.accountObjectRepository = accountObjectRepository;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void createNewAccount(@RequestBody NewAccountDto newAccountDto) {
        commandGateway.send(new CreateAccountCommand(new AccountId(newAccountDto.getAccountNumber()), new OverdraftLimit(newAccountDto.getOverdraft())));
    }

    @GetMapping("/{id}")
    public AccountObject details(@PathVariable("id") String id) {
       return accountObjectRepository.findOne(id);
    }

}
