package com.tilepay.counterpartyclient.service;

import static com.tilepay.counterpartyclient.model.FilterBuilder.aFilter;
import static com.tilepay.counterpartyclient.service.ParamsBuilder.aParams;
import static com.tilepay.counterpartyclient.service.PayloadBuilder.aPayload;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.tilepay.counterpartyclient.config.CounterpartyConfig;
import com.tilepay.counterpartyclient.model.AssetInfo;
import com.tilepay.counterpartyclient.model.CounterpartyBalance;
import com.tilepay.counterpartyclient.model.CounterpartyRawTransaction;
import com.tilepay.counterpartyclient.model.CounterpartySend;
import com.tilepay.counterpartyclient.model.CounterpartyStatus;
import com.tilepay.counterpartyclient.rest.CounterpartyRestTemplate;

@Service
public class CounterpartyService {

    public static final String GET_NORMALIZED_BALANCES = "get_normalized_balances";
    public static final String GET_RAW_TRANSACTIONS = "get_raw_transactions";

    public static final String PROXY_TO_COUNTERPARTYD = "proxy_to_counterpartyd";
    public static final String GET_ASSET_INFO = "get_asset_info";
    public static final String GET_SENDS = "get_sends";
    public static final String GET_BALANCES = "get_balances";
    public static final String TILECOINX = "TILECOINX";

    @Inject
    private CounterpartyConfig counterpartyConfig;

    @Inject
    private CounterpartyRestTemplate counterpartyRestTemplate;

    private static final Gson gson = new Gson();
    
    private static final Logger logger = Logger.getLogger(CounterpartyService.class.getName());

    public CounterpartyService() throws Exception {
        SSLUtil.turnOffSslChecking();
    }

    public List<CounterpartyBalance> getBalancesByAddress(String address) {
        Payload payload = aPayload().setMethod(GET_NORMALIZED_BALANCES).setParams(aParams().setAddresses(address).build()).build();
        return parseResponse(payload, new TypeToken<List<CounterpartyBalance>>() {
        }.getType());
    }

    public List<CounterpartyRawTransaction> getRawTransactions(String address) {
        Payload payload = aPayload().setMethod(GET_RAW_TRANSACTIONS).setParams(aParams().setAddress(address).build()).build();
        return parseResponse(payload, new TypeToken<List<CounterpartyRawTransaction>>() {
        }.getType());
    }

    public List<AssetInfo> getAssetInfos(String... assets) {
        Params params = aParams().setMethod(GET_ASSET_INFO).setParams(aParams().setAssets(assets).build()).build();
        Payload payload = aPayload().setMethod(PROXY_TO_COUNTERPARTYD).setParams(params).build();
        return parseResponse(payload, new TypeToken<List<AssetInfo>>() {
        }.getType());
    }

    public List<CounterpartySend> getSends(String source, String destination) {
        Params params1 = aParams().setFilters(aFilter().setField("source").setOp("==").setValue(source).build(),
                aFilter().setField("destination").setOp("==").setValue(destination).build()).build();
        Params params = aParams().setMethod(GET_SENDS).setParams(params1).build();
        Payload payload = aPayload().setMethod(PROXY_TO_COUNTERPARTYD).setParams(params).build();
        return parseResponse(payload, new TypeToken<List<CounterpartySend>>() {
        }.getType());
    }

    public List<CounterpartyBalance> getBalances(String address, String asset) {
        Params params1 = aParams().setFilters(aFilter().setField("address").setOp("==").setValue(address).build(),
                aFilter().setField("asset").setOp("==").setValue(asset).build()).build();
        Params params = aParams().setMethod(GET_BALANCES).setParams(params1).build();
        Payload payload = aPayload().setMethod(PROXY_TO_COUNTERPARTYD).setParams(params).build();
        return parseResponse(payload, new TypeToken<List<CounterpartyBalance>>() {
        }.getType());
    }

    public <T> T fromJson(String json, Type typeOfT) {
        return new Gson().fromJson(json, typeOfT);
    }

    public AssetInfo getTilecoinXAssetInfo() {
        return getOneAssetInfo(TILECOINX);
    }

    public AssetInfo getOneAssetInfo(String assets) {
        List<AssetInfo> assetInfos = getAssetInfos(assets);
        if (!assetInfos.isEmpty()) {
            return assetInfos.get(0);
        }
        return null;
    }

    public List<CounterpartyBalance> getBalances(String json) {
        return parseResult(json, new TypeToken<List<CounterpartyBalance>>() {
        }.getType());
    }

    public <T> T parseResponse(Payload payload, Type typeOfT) {
        return parseResponse(counterpartyConfig.getServerUrl(), payload, typeOfT);
    }

    public <T> T parseResponse(String serverUrl, Payload payload, Type typeOfT) {

        LinkedTreeMap response;
        try {
            response = counterpartyRestTemplate.postForObject(serverUrl, gson.toJson(payload), LinkedTreeMap.class);
        } catch (CounterpartyIsNotAvailableException e) {
            return createEmptyResponse(typeOfT);
        }

        ////TODO: 10.02.2015 Andrei Sljusar: programmers error. it's ok to throw exception?
        Object error = response.get("error");
        if (error != null) {
        	logger.info("identifying error in parseResponse: " + error.toString() );
            throw new CounterpartyErrorException((AbstractMap) error);
        }
        Object result = response.get("result");
        return parseResult(gson.toJson(result), typeOfT);
    }

    private <T> T createEmptyResponse(Type typeOfT) {
        try {
            ParameterizedType parameterizedType = (ParameterizedType) typeOfT;
            Type rawType = parameterizedType.getRawType();
            if (rawType.getTypeName().equals("java.util.List")) {
                Class myClass = Class.forName("java.util.ArrayList");
                Constructor constructor = myClass.getConstructor();
                return (T) constructor.newInstance();
            } else {
                throw new IllegalArgumentException("Unknown type: " + rawType.getTypeName());
            }
        } catch (InvocationTargetException | ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T parseResult(String result, Type typeOfT) {
        return fromJson(result, typeOfT);
    }

    public List<AssetInfo> parseAssetInfos(String json) {
        return parseResult(json, new TypeToken<List<AssetInfo>>() {
        }.getType());
    }

    public List<CounterpartySend> parseSends(String json) {
        return parseResult(json, new TypeToken<List<CounterpartySend>>() {
        }.getType());
    }

    public CounterpartyStatus getStatus(String url) {
        try {
            return counterpartyRestTemplate.getForObject(url, CounterpartyStatus.class);
        } catch (CounterpartyIsNotAvailableException e) {
            return e.getCounterpartyStatus();
        }
    }

    public CounterpartyStatus getStatus() {
        return getStatus(counterpartyConfig.getServerUrl());
    }
}