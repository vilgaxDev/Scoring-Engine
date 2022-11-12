package com.scoringengine.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class CreateClient implements Serializable {
    String url;
    String name;
    String username;
    String password;
}
