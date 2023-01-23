package com.creasmit.allolecolebackend.service;

import com.creasmit.allolecolebackend.model.SmsPayload;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SmsServiceImpl {

    public ResponseEntity sendSms(String to, String content) throws Exception {
        System.out.println("Send sms to :" + to);

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);

        SmsPayload smsPayload = new SmsPayload();
        smsPayload.setFrom("ALLO LECOLE");
        smsPayload.setTo(to);
        smsPayload.setContent(content);


        MediaType mediaType = MediaType.parse("application/json");
        Request request = new Request.Builder()
                .url("http://api2.dream-digital.info/api/SendSMSMulti?api_id=API11953300243&api_password=JmjHbEl4st&sms_type=T&encoding=T&sender_id=ALLOLECOLE&phonenumber=" + smsPayload.getTo() + "&textmessage=" + smsPayload.getContent())
                .build();
        Response response = client.newCall(request).execute();
        String bodyData = response.body().string();

        String token = response.headers().get(HttpHeaders.AUTHORIZATION);
        System.out.println("Token: " + token);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.AUTHORIZATION, token);

        return ResponseEntity.status(response.code())
                .headers(httpHeaders)
                .body(bodyData);
    }


}
