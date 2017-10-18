package com.borysfan.account;

import com.borysfan.core.AccountId;
import com.borysfan.core.Amount;
import com.borysfan.core.api.*;
import com.borysfan.mortage.TrancheTransferSaga;
import org.axonframework.test.matchers.Matchers;
import org.axonframework.test.saga.SagaTestFixture;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

public class TrancheTransferTest {

    private SagaTestFixture<TrancheTransferSaga> fixture;

    private final String mortageId = "M1";
    private final AccountId accountId = new AccountId("A1");
    private final Amount amount = new Amount(100L);
    private final String trancheId = "T1";


    @Before
    public void before() {
        fixture = new SagaTestFixture<>(TrancheTransferSaga.class);
    }

    @Test
    public void shouldCreateNewSageForHandlingTrancheTransfer() {
        fixture.givenNoPriorActivity()
                .whenPublishingA(new TransferTrancheRequestedEvent(trancheId, mortageId, accountId, amount))
                .expectActiveSagas(1)
                .expectDispatchedCommands(new SubtractMoneyFromMortageCommand(mortageId, amount));
    }

    @Test
    public void shouldSubtractMoneyFromMortage() {
        fixture.givenAPublished(new TransferTrancheRequestedEvent(trancheId, mortageId, accountId, amount))
                .whenPublishingA(new MoneySubtractedFromMortageEvent(mortageId, new Amount(900L)))
                .expectActiveSagas(1)
                .expectDispatchedCommandsMatching(
                        Matchers.exactSequenceOf(DepositCommandMatcher.newInstance(new DepositMoneyCommand(accountId, "",  amount))));
    }

    public static class DepositCommandMatcher extends BaseCommandMatcher<DepositMoneyCommand> {

        private DepositMoneyCommand command;

        private DepositCommandMatcher(DepositMoneyCommand command) {
            this.command = command;
        }

        public static Matcher newInstance(DepositMoneyCommand command) {
            return new DepositCommandMatcher(command);
        }

        @Override
        protected boolean doMatches(DepositMoneyCommand message) {
            return command.getAmount().equals(message.getAmount());
        }

        @Override
        public void describeTo(Description description) {
            description.appendText("It does not match!!!");
        }
    }

}
