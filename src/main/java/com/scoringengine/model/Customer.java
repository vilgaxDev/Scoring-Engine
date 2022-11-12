package com.scoringengine.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Table(name="customers")
public class Customer {

    @JsonIgnore
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long customerId;
    public String customerNumber;
    public String username;
    public String password;
    public String status;

    @JsonIgnore
    @OneToMany()
    @JoinColumn(name = "customerId")
    List<LoanBook> loanBooks;
}
