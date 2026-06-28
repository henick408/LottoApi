package org.henick.lottoapi.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
class LottoApiConfig {

    @Bean
    RestClient lottoRestClient(
            RestClient.Builder builder,
            @Value("${lotto-api.base-url}") String baseUrl,
            @Value("${lotto-api.secret}") String secret
    ) {
        return builder
                .baseUrl(baseUrl)
                .defaultHeader("secret", secret)
                .build();
    }

}
