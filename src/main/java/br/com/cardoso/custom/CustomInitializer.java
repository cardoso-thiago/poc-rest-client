package br.com.cardoso.custom;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;

import java.util.UUID;

public class CustomInitializer implements ClientHttpRequestInitializer {

    @Override
    public void initialize(ClientHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        headers.add("custom-header", UUID.randomUUID().toString());
    }
}