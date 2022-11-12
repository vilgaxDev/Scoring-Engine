package com.scoringengine.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Data
@Component
public class ScoringResults implements Serializable {
    String id;
    String customerNumber;
    String score;
    int limitAmount;
    String exclusion;
    String exclusionReason;
}
