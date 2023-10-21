package br.com.cardoso.service;

import br.com.cardoso.core.RestClientService;
import br.com.cardoso.custom.CustomInitializer;
import br.com.cardoso.exception.BaseUrlConfigNotFound;
import org.springframework.stereotype.Service;

@Service
public class TestRestClientService {

    private final RestClientService restClientService;

    public TestRestClientService(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    public String getTest() throws BaseUrlConfigNotFound {
        return restClientService
                .identifier("test")
                .generateToken()
                .restClient()
                .mutate()
                .requestInitializer(new CustomInitializer())
                .build()
                .get()
                .uri("/test")
                .retrieve()
                .body(String.class);
    }
}
