package br.com.cardoso.core;

import br.com.cardoso.exception.ConfigurationNotFound;
import br.com.cardoso.initializer.TokenInitializer;
import org.springframework.core.env.Environment;
import org.springframework.web.client.RestClient;

import java.text.MessageFormat;

public class RestClientService {

    private final Environment environment;
    private final RestClient defaultRestClient;
    private String identifier;
    private boolean generateToken;

    public RestClientService(Environment environment, RestClient defaultRestClient) {
        this.environment = environment;
        this.defaultRestClient = defaultRestClient;
    }

    public RestClientService identifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public RestClient restClient() throws ConfigurationNotFound {
        String configKeyBaseUrl = identifier + ".api.uri";
        String baseUrl = getEnvConfig(configKeyBaseUrl);
        RestClient.Builder builder = this.defaultRestClient.mutate().baseUrl(baseUrl);
        if(generateToken) {
            String configKeyAuthBaseUrl = identifier + ".auth.api.uri";
            String configValueAuthBaseUrl = getEnvConfig(configKeyAuthBaseUrl);
            builder.requestInitializer(new TokenInitializer(configValueAuthBaseUrl));
        }
        return builder.build();
    }

    public RestClientService generateToken() {
        this.generateToken = true;
        return this;
    }

    private String getEnvConfig(String configKey) throws ConfigurationNotFound {
        String configValue = environment.getProperty(configKey);
        if (configValue == null) {
            throw new ConfigurationNotFound(MessageFormat.format("Configuration {0} not found", configKey));
        }
        return configValue;
    }
}
