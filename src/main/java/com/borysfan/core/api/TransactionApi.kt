package com.borysfan.core.api

import com.borysfan.core.AccountId
import com.borysfan.core.Amount

class RequestTransactionCommand(val transactionId: String,
                                val fromAccountId: AccountId,
                                val toAccountId: AccountId,
                                val amount: Amount)

class TransactionRequestedEvent(val transactionId: String,
                                val fromAccountId: AccountId,
                                val toAccountId: AccountId,
                                val amount: Amount)

class CompleteTransactionCommand(val transactionId: String)

class TransactionCompletedEvent(val transactionId: String)

class CancelTransactionCommand(val transactionId: String)

class TransactionCancelledEvent(val transactionId: String)

