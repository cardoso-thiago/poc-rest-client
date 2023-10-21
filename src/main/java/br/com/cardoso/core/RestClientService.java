package br.com.cardoso.core;

import br.com.cardoso.exception.BaseUrlConfigNotFound;
import br.com.cardoso.initializer.TokenInitializer;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;

public class RestClientService {

    private final Environment environment;
    private final RestClient defaultRestClient;

    private RestClient restClient;
    private String identifier;
    private String baseUrl;

    public RestClientService(Environment environment, RestClient defaultRestClient) {
        this.environment = environment;
        this.defaultRestClient = defaultRestClient;
    }

    public RestClientService identifier(String identifier) throws BaseUrlConfigNotFound {
        this.identifier = identifier;
        String configKey = identifier + ".api.uri";
        this.baseUrl = environment.getProperty(configKey);
        if (baseUrl == null) {
            throw new BaseUrlConfigNotFound(MessageFormat.format("Configuration {} not found", configKey));
        }
        this.restClient = this.defaultRestClient.mutate().baseUrl(baseUrl).build();
        return this;
    }

    public RestClient restClient() {
        return this.restClient;
    }

    public RestClientService generateToken() throws BaseUrlConfigNotFound {
        String configKey = identifier + ".auth.api.uri";
        String authBaseUrl = environment.getProperty(configKey);
        if (authBaseUrl == null) {
            throw new BaseUrlConfigNotFound(MessageFormat.format("Configuration {} not found", configKey));
        }
        this.restClient = this.defaultRestClient.mutate().requestInitializer(new TokenInitializer(authBaseUrl)).baseUrl(baseUrl).build();
        return this;
    }
}
