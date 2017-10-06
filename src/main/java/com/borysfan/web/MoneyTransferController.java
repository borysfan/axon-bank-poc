package com.borysfan.web;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.api.DepositMoneyCommand;
import com.borysfan.core.api.RequestTransactionCommand;
import com.borysfan.core.api.WithdrawMoneyCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class MoneyTransferController {

    private final CommandGateway commandGateway;

    public MoneyTransferController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/withdraws")
    public void withdraws(@RequestBody NewWithdrawDto newWithdrawDto) {
        commandGateway.send(new WithdrawMoneyCommand(new AccountId(newWithdrawDto.getAccountNumber()), UUID.randomUUID().toString(), new Amount(newWithdrawDto.getAmount())));
    }

    @PostMapping("/deposits")
    public void deposits(@RequestBody NewDepositDto newDepositDto) {
        commandGateway.send(new DepositMoneyCommand(new AccountId(newDepositDto.getAccountNumber()), UUID.randomUUID().toString(), new Amount(newDepositDto.getAmount())));
    }

    @PostMapping("/transactions")
    public void transactions(@RequestBody NewTransferDto newTransferDto) {
        commandGateway.send(new RequestTransactionCommand(UUID.randomUUID().toString(), new AccountId(newTransferDto.getFromAccountNumber()), new AccountId(newTransferDto.getToAccountNumber()), new Amount(newTransferDto.getAmount())));
    }

}
