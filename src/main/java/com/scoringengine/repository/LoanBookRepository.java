package com.scoringengine.repository;

import com.scoringengine.model.Customer;
import com.scoringengine.model.LoanBook;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanBookRepository extends CrudRepository<LoanBook, Long> {
    LoanBook findByLoanId(Long customerId);

    LoanBook findByCustomer(Customer customerId);
}
