package com.scoringengine.repository;

import com.scoringengine.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer,Long> {
    Customer findByCustomerId(Long customerId);
    Customer findByCustomerNumber(String customerId);
}
