package com.borysfan.mortage;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.InsufficientAmountOfMoneyException;
import com.borysfan.core.api.*;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.commandhandling.model.AggregateIdentifier;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;

import static org.axonframework.commandhandling.model.AggregateLifecycle.apply;

@Aggregate
public class Mortage {

    @AggregateIdentifier
    private String mortageId;

    private Amount amountToTransfer;

    private AccountId accountId;

    public Mortage(){

    }

    @CommandHandler
    public Mortage(CreateMortageCommand command) {
        apply(new MortageCreatedEvent(command.getMortageId(), command.getAccountId(), command.getAmount()));
    }

    @EventSourcingHandler
    public void on(MortageCreatedEvent event) {
        this.mortageId = event.getMortageId();
        this.amountToTransfer = event.getAmount();
        this.accountId = event.getAccountId();
    }

    @CommandHandler
    public void handle(TransferTrancheCommand command)  {
        apply(new TransferTrancheRequestedEvent(command.getTrancheTransferId(), command.getMortageId(), accountId, command.getAmount()));
    }

    @CommandHandler
    public void handle(SubtractMoneyFromMortageCommand command) throws InsufficientAmountOfMoneyException {
        if (amountToTransfer.isLess(command.getAmount())) {
            throw new InsufficientAmountOfMoneyException();
        } else {
            apply(new MoneySubtractedFromMortageEvent(mortageId, amountToTransfer.subtract(command.getAmount())));
        }
    }

    @EventSourcingHandler
    public void on(MoneySubtractedFromMortageEvent event) {
        this.amountToTransfer = event.getAmountToTransfer();
    }

}
