package com.borysfan.mortage;

import com.borysfan.core.api.MoneySubtractedFromMortageEvent;
import com.borysfan.core.api.MortageCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class MortageDetails {

    private final MortageObjectRepository mortageObjectRepository;

    public MortageDetails(MortageObjectRepository mortageObjectRepository) {
        this.mortageObjectRepository = mortageObjectRepository;
    }

    @EventHandler
    public void on(MortageCreatedEvent event) {
        mortageObjectRepository.save(new MortageObject(event.getMortageId(), event.getAmount().asLong()));
    }

    @EventHandler
    public void on(MoneySubtractedFromMortageEvent event) {
        MortageObject mortageObject = mortageObjectRepository.findOne(event.getMortageId());
        mortageObject.setMoneyToTransfer(event.getAmountToTransfer().asLong());
        mortageObjectRepository.save(mortageObject);
    }

}
