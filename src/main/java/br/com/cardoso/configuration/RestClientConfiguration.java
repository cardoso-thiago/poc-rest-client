package br.com.cardoso.configuration;

import br.com.cardoso.core.RestClientService;
import br.com.cardoso.initializer.CorrelationIdInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfiguration {

    @Bean
    public RestClient defaultRestClient() {
        return RestClient.builder().requestInitializer(new CorrelationIdInitializer()).build();
    }

    @Bean
    public RestClientService restClientService(Environment environment, RestClient defaultRestClient) {
        return new RestClientService(environment, defaultRestClient);
    }
}
