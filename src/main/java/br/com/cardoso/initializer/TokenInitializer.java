package br.com.cardoso.initializer;

import br.com.cardoso.model.Token;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestInitializer;
import org.springframework.web.client.RestClient;

public class TokenInitializer implements ClientHttpRequestInitializer {

    private final String authBaseUrl;

    public TokenInitializer(String authBaseUrl) {
        this.authBaseUrl = authBaseUrl;
    }

    @Override
    public void initialize(ClientHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        Token token = RestClient.create(authBaseUrl).get().uri("/token").retrieve().body(Token.class);
        if (token != null) {
            String accessToken = token.accessToken();
            headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
        }
    }
}
