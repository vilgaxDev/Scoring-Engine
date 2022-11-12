package com.scoringengine.service;


import com.scoringengine.dto.ClientResponse;
import com.scoringengine.dto.CreateClient;
import com.scoringengine.dto.ScoringResults;
import org.springframework.http.*;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class ScoringClient {


    @Retryable(maxAttempts = 5, value = RuntimeException.class,backoff = @Backoff(delay = 15000, multiplier = 2))
    public String getScoringToken(String customerNumber) {
        RestTemplate restTemplate = new RestTemplate();
        String scoring_url = "https://scoringtest.credable.io/ap1/vi/scoring/initiateQueryScore/" + customerNumber;
        ResponseEntity<String> response =
                restTemplate.getForEntity(
                        scoring_url,
                        String.class);
        return response.getBody();
    }

    @Retryable(maxAttempts = 5, value = RuntimeException.class,
            backoff = @Backoff(delay = 15000, multiplier = 2))
    public ScoringResults getScoring(String token) {
        CreateClient createClient=new CreateClient();
        createClient.setName("credable_engine");
        createClient.setUrl("http://localhost:8080/");
        createClient.setUsername("admin");
        createClient.setPassword("psw123");
        ClientResponse clientResponse=createClient(createClient);

        String scoring_url = "https://scoringtest.credable.io/ap1/vi/scoring/queryScore/" + token;
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(clientResponse.getToken());
        ResponseEntity<ScoringResults> response =
                restTemplate.getForEntity( scoring_url,
                        ScoringResults.class);
        return response.getBody();
    }

    @Retryable(maxAttempts = 5, value = RuntimeException.class,
            backoff = @Backoff(delay = 15000, multiplier = 2))
    public ClientResponse createClient(CreateClient clientRequest) {
        String scoring_url = "https://scoringtest.credable.io/ap1/vi/client/createClient";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<CreateClient> request = new HttpEntity<>(clientRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ClientResponse> response = restTemplate
                .exchange(scoring_url, HttpMethod.POST, request, ClientResponse.class);
        return response.getBody();
    }


}

