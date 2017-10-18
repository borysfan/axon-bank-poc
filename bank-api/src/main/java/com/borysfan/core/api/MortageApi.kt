package com.borysfan.core.api

import com.borysfan.core.AccountId
import com.borysfan.core.Amount
import org.axonframework.commandhandling.TargetAggregateIdentifier

class CreateMortageCommand(@TargetAggregateIdentifier val mortageId:String, val accountId: AccountId, val amount: Amount)

class MortageCreatedEvent(val mortageId: String, val accountId: AccountId, val amount: Amount)

class TransferTrancheCommand( val trancheTransferId:String, @TargetAggregateIdentifier val mortageId:String, val amount: Amount)

class TransferTrancheRequestedEvent(val trancheTransferId: String, val mortageId: String, val accountId: AccountId, val amount: Amount)

class SubtractMoneyFromMortageCommand(@TargetAggregateIdentifier val mortageId: String, val amount: Amount)

class MoneySubtractedFromMortageEvent(val mortageId: String, val amountToTransfer: Amount)
