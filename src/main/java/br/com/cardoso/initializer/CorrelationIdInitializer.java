package br.com.cardoso.initializer;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;

public class CorrelationIdInitializer implements ClientHttpRequestInitializer {

    @Override
    public void initialize(ClientHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        if (!headers.containsKey("correlation-id")) {
            String correlationId = MDC.get("correlationId");
            headers.add("correlation-id", correlationId);
        }
    }
}
