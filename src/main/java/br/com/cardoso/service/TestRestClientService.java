package br.com.cardoso.service;

import br.com.cardoso.controller.TestController;
import br.com.cardoso.core.RestClientService;
import br.com.cardoso.custom.CustomInitializer;
import br.com.cardoso.custom.CustomInitializer2;
import br.com.cardoso.exception.ConfigurationNotFound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class TestRestClientService {

    Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private final RestClientService restClientService;

    public TestRestClientService(RestClientService restClientService) {
        this.restClientService = restClientService;
    }

    public String getTest() throws ConfigurationNotFound {
        String responseCallWithoutToken = restClientService
                .identifier("test2")
                .restClient()
                .mutate()
                .requestInitializer(new CustomInitializer2())
                .build()
                .get()
                .uri("/test")
                .retrieve()
                .body(String.class);

        LOGGER.info(MessageFormat.format("Retorno da chamada sem o token: {0}", responseCallWithoutToken));

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
