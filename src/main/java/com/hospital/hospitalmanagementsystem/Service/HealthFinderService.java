package com.hospital.hospitalmanagementsystem.Service;

import com.hospital.hospitalmanagementsystem.Response.HealthFinderResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class HealthFinderService {
    private final WebClient webClient;

    @Value("${myhealthfinder.api.base-url}")
    private String baseUrl;

    @Value("${spring.codec.max-in-memory-size}")
    private int maxInMemorySize;

    public HealthFinderService() {
        // Increase the buffer size
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(1048576))
                .build();

        this.webClient = WebClient.builder()
                .exchangeStrategies(strategies)
                .baseUrl("https://health.gov/myhealthfinder/api/v3")
                .build();
    }

    public Mono<String> getHealthFinderData(int age, String sex, String pregnant, String sexuallyActive, String tobaccoUse) {
        /*int age = healthFinderResponse.getAge();
        String sex = healthFinderResponse.getSex();
        String pregnant = healthFinderResponse.getPregnant();
        String sexuallyActive = healthFinderResponse.getSexuallyActive();
        String tobaccoUse = healthFinderResponse.getTobaccoUse();*/
        return webClient.get()
                .uri("/myhealthfinder.json?age={age}&sex={sex}&pregnant={pregnant}&sexuallyActive={sexuallyActive}&tobaccoUse={tobaccoUse}",
                        age, sex, pregnant, sexuallyActive, tobaccoUse)
                .retrieve()
                .bodyToMono(String.class);
    }
}