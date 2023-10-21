package br.com.cardoso.controller;

import br.com.cardoso.exception.ConfigurationNotFound;
import br.com.cardoso.model.Token;
import br.com.cardoso.service.TestRestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.UUID;

@RestController
public class TestController {

    Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private final TestRestClientService testRestClientService;

    public TestController(TestRestClientService testRestClientService) {
        this.testRestClientService = testRestClientService;
    }

    @GetMapping("/test")
    public String test(@RequestHeader Map<String, String> headers) {
        headers.forEach((key, value) -> LOGGER.info(String.format("Header '%s' = %s", key, value)));
        return "test";
    }

    @GetMapping("/token")
    public Token token() {
        return new Token(UUID.randomUUID().toString(), UUID.randomUUID().toString());
    }

    @GetMapping("/restclient")
    public String restClient() throws ConfigurationNotFound {
        MDC.put("correlationId", UUID.randomUUID().toString());
        return testRestClientService.getTest();
    }
}
