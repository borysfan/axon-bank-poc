package com.borysfan.web;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.PhoneNumber;
import com.borysfan.core.api.RequestTopUpTelephoneCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/topUps")
public class TopUpController {

    private final CommandGateway commandGateway;

    public TopUpController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public void topUpTelephone(@RequestBody NewTopUpDto newTopUpDto) {
        commandGateway.send(new RequestTopUpTelephoneCommand(UUID.randomUUID().toString(), new AccountId(newTopUpDto.getAccountNumber()),
                new Amount(newTopUpDto.getAmount()), new PhoneNumber(newTopUpDto.getPhoneNumber())));
    }
}
