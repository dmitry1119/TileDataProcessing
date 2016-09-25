package com.tilepay.core.service.balance;

import static com.tilepay.domain.entity.AssetBuilder.anAsset;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.tilepay.core.model.Address;
import com.tilepay.core.model.AssetTransaction;
import com.tilepay.core.service.AddressService;
import com.tilepay.core.service.AssetConverter;
import com.tilepay.core.service.AssetQuantityConversionService;
import com.tilepay.core.service.AssetTransactionService;
import com.tilepay.core.service.BalanceConverter;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.counterpartyclient.model.CounterpartyRawTransaction;
import com.tilepay.counterpartyclient.service.CounterpartyService;
import com.tilepay.domain.entity.Asset;
import com.tilepay.domain.entity.Balance;
import com.tilepay.domain.entity.CurrencyEnum;

@Service
public class CounterpartyBalanceService extends AbstractBalanceService {

    @Inject
    private AssetTransactionService assetTransactionService;

    @Inject
    private CounterpartyService counterpartyService;

    @Inject
    private AddressService addressService;

    @Inject
    private AssetQuantityConversionService assetQuantityConversionService;

    @Inject
    private AssetConverter assetConverter;

    @Inject
    private BalanceConverter balanceConverter;

    public CounterpartyBalance getCurrencyBalance(String address, Asset currency) {
        List<CounterpartyBalance> counterpartyBalances = getBalancesByAddress(address);

        for (CounterpartyBalance counterpartyBalance : counterpartyBalances) {
            if (counterpartyBalance.getAsset().equalsIgnoreCase(currency.getName())) {
                return counterpartyBalance;
            }
        }
        throw new RuntimeException("TODO: No Balance for address: " + address + " and currency: " + currency.getName());
    }

    public List<CounterpartyBalance> getBalancesByAddress(String address) {
        List<CounterpartyBalance> counterpartyBalances = counterpartyService.getBalancesByAddress(address);
        return getBalancesWithTilecoinX(counterpartyBalances, address);
    }

    public List<CounterpartyBalance> getBalancesWithTilecoinX(List<CounterpartyBalance> counterpartyBalances, String address) {

        CounterpartyBalance tilecoinXBalance = null;

        Iterator<CounterpartyBalance> iterator = counterpartyBalances.iterator();
        while (iterator.hasNext()) {
            CounterpartyBalance counterpartyBalance = iterator.next();
            if (counterpartyBalance.getAsset().equalsIgnoreCase(CurrencyEnum.TILECOINX.name())) {
                tilecoinXBalance = counterpartyBalance;
                iterator.remove();
                break;
            }
        }

        AssetInfo tilecoinXAssetInfo = counterpartyService.getTilecoinXAssetInfo();

        if (tilecoinXAssetInfo != null) {
            counterpartyBalances.add(0, tilecoinXBalance == null ? new CounterpartyBalance(address, CurrencyEnum.TILECOINX.name()) : tilecoinXBalance);
        }

        return counterpartyBalances;
    }

    public void setEstimatedBalances(List<CounterpartyBalance> counterpartyBalances) {
        for (CounterpartyBalance counterpartyBalance : counterpartyBalances) {
            counterpartyBalance.setEstimatedQuantity(getEstimatedBalance(counterpartyBalance));
        }
    }

    @Override
    public BigInteger getEstimatedBalance(String address, Asset asset) {
        CounterpartyBalance currencyCounterpartyBalance = getCurrencyBalance(address, asset);
        return BigInteger.valueOf(getEstimatedBalance(currencyCounterpartyBalance));
    }

    public long getEstimatedBalance(CounterpartyBalance counterpartyBalance) {
        Address address = addressService.findOneByAddress(counterpartyBalance.getAddress());
        Asset asset = assetConverter.getAsset(counterpartyBalance.getAsset());
        List<AssetTransaction> TxList = assetTransactionService.findAllWhereAddressAndCurrency(address, asset.getName());
        List<CounterpartyRawTransaction> rawTxList = counterpartyService.getRawTransactions(address.getAddress());

        BigInteger assetInTx = new BigInteger("0");
        for (AssetTransaction aTxList : TxList) {
            if (containsHash(rawTxList, aTxList.getHash())) {
                assetTransactionService.deleteOneByHash(aTxList.getHash());
            } else {
                assetInTx = assetInTx.add(assetQuantityConversionService.formatAsBigInteger(asset, aTxList.getAmount().toPlainString()));
            }

        }
        return counterpartyBalance.getQuantityAsBigInteger().subtract(assetInTx).longValue();
    }

    private boolean containsHash(List<CounterpartyRawTransaction> rawTxList, String hash) {
        for (CounterpartyRawTransaction aRawTxList : rawTxList) {
            // TODO: Dec 4, 2014 Mait Mikkelsaar: Differentiate objects in rawTxList
            if (aRawTxList.getTx_hash() != null && aRawTxList.getTx_hash().equals(hash)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Balance> getBalances(String address) {
        List<CounterpartyBalance> counterpartyBalances = getBalancesByAddress(address);
        setEstimatedBalances(counterpartyBalances);
        return balanceConverter.getBalances(counterpartyBalances);
    }

    @Override
    public void setAssetDivisibility(Asset asset) {
        assetConverter.setAssetDivisibility(asset);
    }

    public BigInteger getTilecoinXEstimatedBalance(String address) {
        Asset tilecoinX = anAsset().setTilecoinx().build();
        return getEstimatedBalance(address, tilecoinX);
    }
}
