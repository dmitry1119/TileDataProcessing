package com.tilepay.counterpartyclient.service;

import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import com.tilepay.counterpartyclient.model.CounterpartyStatusBuilder;

public class CounterpartyIsNotAvailableException extends CounterpartyException {

    private CounterpartyStatus counterpartyStatus;

    public CounterpartyIsNotAvailableException(String s) {
        super(s);
        this.counterpartyStatus = CounterpartyStatusBuilder.aCounterpartyStatus().setCounterpartyd("NOT_OK").build();
    }

    public CounterpartyIsNotAvailableException(CounterpartyStatus counterpartyStatus) {
        super(counterpartyStatus.toString());
        this.counterpartyStatus = counterpartyStatus;
    }

    public CounterpartyStatus getCounterpartyStatus() {
        return counterpartyStatus;
    }
}