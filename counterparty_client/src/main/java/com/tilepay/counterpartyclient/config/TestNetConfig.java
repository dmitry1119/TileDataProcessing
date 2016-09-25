package com.tilepay.counterpartyclient.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile(value = { "default", "testnet" })
@Configuration
public class TestNetConfig extends CounterpartyConfig {

    @Override
    public String getServerUrl() {
        return "https://cw01.counterwallet.io/_t_api";
        //return "https://cw03.counterwallet.io/_t_api";
    }

}
