package com.borysfan.core.api

import com.borysfan.core.AccountId
import com.borysfan.core.Amount
import com.borysfan.core.PhoneNumber
import org.axonframework.commandhandling.TargetAggregateIdentifier

class RequestTopUpTelephoneCommand(@TargetAggregateIdentifier val transactionId:String, val accountId: AccountId, val amount: Amount, val phoneNumber: PhoneNumber)

class CompleteTopUpTelephoneCommand(@TargetAggregateIdentifier val transactionId: String)

class TelephoneTopUpRequestedEvent(val transactionId:String, val accountId: AccountId, val amount: Amount, val phoneNumber: PhoneNumber)

class TopUpCompletedEvent(val transactionId: String)