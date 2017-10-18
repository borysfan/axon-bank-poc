package com.borysfan.account;

import org.axonframework.commandhandling.CommandMessage;
import org.hamcrest.BaseMatcher;

public abstract class BaseCommandMatcher<T> extends BaseMatcher<T> {
    @Override
    public final boolean matches(Object o) {
        if (!(o instanceof CommandMessage)) {
            return false;
        }
        CommandMessage<T> message = (CommandMessage<T>) o;

        return doMatches(message.getPayload());
    }

    protected abstract boolean doMatches(T message);
}
