package com.tilepay.counterpartyclient.service;

import java.util.AbstractMap;

import com.tilepay.counterpartyclient.model.CounterpartyError;

public class CounterpartyErrorException extends CounterpartyException {

    public CounterpartyErrorException(AbstractMap error) {
        super(getCounterpartyError(error).toString());
    }

    public CounterpartyErrorException(CounterpartyError counterpartyError) {
        super(counterpartyError.toString());
    }

    private static CounterpartyError getCounterpartyError(Object error) {
        return gson.fromJson(gson.toJson(error), CounterpartyError.class);
    }
}