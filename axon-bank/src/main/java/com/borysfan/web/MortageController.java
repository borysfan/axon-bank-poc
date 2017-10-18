package com.borysfan.web;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.api.CreateMortageCommand;
import com.borysfan.core.api.TransferTrancheCommand;
import com.borysfan.mortage.MortageObject;
import com.borysfan.mortage.MortageObjectRepository;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

@RestController
public class MortageController {

    private final CommandGateway commandGateway;
    private final MortageObjectRepository mortageObjectRepository;

    public MortageController(CommandGateway commandGateway, MortageObjectRepository mortageObjectRepository) {
        this.commandGateway = commandGateway;
        this.mortageObjectRepository = mortageObjectRepository;
    }

    @PostMapping("/mortages")
    public void create(@RequestBody NewMortageDto newMortageDto) {
        commandGateway.send(new CreateMortageCommand(newMortageDto.getMortageId(), new AccountId(newMortageDto.getAccountId()), new Amount(newMortageDto.getAmount())));
    }

    @PostMapping("/tranches")
    public void transferTranche(@RequestBody NewTrancheDto newTrancheDto) {
        commandGateway.send(new TransferTrancheCommand(newTrancheDto.getTrancheId(), newTrancheDto.getMortageId(), new Amount(newTrancheDto.getAmount())));
    }

    @GetMapping("/mortages/{id}")
    public MortageObject details(@PathVariable("id") String id) {
        return mortageObjectRepository.findOne(id);
    }


}
