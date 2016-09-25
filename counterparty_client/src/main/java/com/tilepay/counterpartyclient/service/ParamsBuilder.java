package com.tilepay.counterpartyclient.service;

import java.util.Arrays;
import java.util.List;

import com.tilepay.counterpartyclient.model.Filter;

public class ParamsBuilder {
    private String method;
    private Params params;
    private List<String> addresses;
    private String address;
    private List<String> assets;
    private List<Filter> filters;

    public ParamsBuilder setAddresses(List<String> addresses) {
        this.addresses = addresses;
        return this;
    }

    public ParamsBuilder setAddresses(String... addresses) {
        return setAddresses(Arrays.asList(addresses));
    }

    public ParamsBuilder setAddress(String address) {
        this.address = address;
        return this;
    }

    public ParamsBuilder setAssets(String... assets) {
        return setAssets(Arrays.asList(assets));
    }

    public ParamsBuilder setAssets(List<String> assets) {
        this.assets = assets;
        return this;
    }

    public ParamsBuilder setMethod(String method) {
        this.method = method;
        return this;
    }

    public ParamsBuilder setParams(Params params) {
        this.params = params;
        return this;
    }

    public ParamsBuilder setFilters(List<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public ParamsBuilder setFilters(Filter... filters) {
        return setFilters(Arrays.asList(filters));
    }

    public static ParamsBuilder aParams() {
        return new ParamsBuilder();
    }

    public Params build() {
        Params object = new Params();
        object.setMethod(method);
        object.setParams(params);
        object.setAddresses(addresses);
        object.setAddress(address);
        object.setAssets(assets);
        object.setFilters(filters);
        return object;
    }

}
