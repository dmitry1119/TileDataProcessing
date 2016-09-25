package com.tilepay.web.controller;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class AccountControllerTest extends AbstractControllerTest {

    @Test
    @Ignore("TODO: FIX")
    public void create() throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        /*HttpPost httpPost = new HttpPost(applicationUrl + "/create");

        ArrayList<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("passPhrase", "shove thought scar distant trickle tight story bare forehead"));
        postParameters.add(new BasicNameValuePair("wallet.address.address", "123456789"));
        postParameters.add(new BasicNameValuePair("accountType", "INDIVIDUAL"));
        httpPost.setEntity((new UrlEncodedFormEntity(postParameters)));

        CloseableHttpResponse response = httpclient.execute(httpPost);
        int statusCode = response.getStatusLine().getStatusCode();
        assertEquals(200, statusCode);*/
    }
}