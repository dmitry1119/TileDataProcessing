package com.tilepay.counterpartyclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "mainnet" })
@Configuration
public class MainNetConfig extends CounterpartyConfig {

    @Override
    public String getServerUrl() {
        return "https://cw01.counterwallet.io/_api";
    }
}
