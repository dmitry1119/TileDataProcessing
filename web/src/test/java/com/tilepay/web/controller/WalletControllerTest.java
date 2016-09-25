package com.tilepay.web.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class WalletControllerTest extends AbstractControllerTest{

    @Test
    public void index() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        /*HttpGet httpGet = new HttpGet(applicationUrl + "/wallet");

        CloseableHttpResponse response = httpclient.execute(httpGet);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);*/
    }

}
