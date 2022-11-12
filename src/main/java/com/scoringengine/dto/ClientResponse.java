package com.scoringengine.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class ClientResponse implements Serializable {
    String id;
    String url;
    String name;
    String username;
    String password;
    String token; // use this to make call for scoring
}
