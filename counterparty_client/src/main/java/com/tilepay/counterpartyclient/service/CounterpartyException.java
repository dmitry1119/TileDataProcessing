package com.tilepay.counterpartyclient.service;

import com.google.gson.Gson;

public abstract class CounterpartyException extends RuntimeException {

    protected static final Gson gson = new Gson();

    public CounterpartyException(String s) {
        super(s);
    }


}