package com.tilepay.core.service.balance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.config.NetworkParams;
import com.tilepay.core.config.TestNet3ParamsConfig;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;

@Service
public class TilecoinBalanceService extends AbstractBalanceService {

    @Inject
    private TilecoinRestClient tilecoinRestClient;

    @Inject
    private NetworkParams networkParams;

    //TODO: 13.01.2015 Andrei Sljusar: fix
    @Override
    public BigInteger getEstimatedBalance(String address, Asset asset) {
        Balance balance = getBalance(address, asset);
        return balance.getQuantity();
    }

    @Override
    public List<Balance> getBalances(String address) {
        List<Balance> balances = new ArrayList<>();

        if (networkParams instanceof TestNet3ParamsConfig) {
            balances = tilecoinRestClient.getBalances(address);
            for (Balance balance : balances) {
                balance.setEstimatedQuantity(balance.getQuantity());
            }
        }
        return balances;
    }

    @Override
    public void setAssetDivisibility(Asset asset) {
        Boolean divisible = tilecoinRestClient.getAssetByName(asset.getName()).getDivisible();
        asset.setDivisible(divisible);
    }

    public Balance getBalance(String source, Asset asset) {
        return tilecoinRestClient.getBalance(source, asset);
    }
}
