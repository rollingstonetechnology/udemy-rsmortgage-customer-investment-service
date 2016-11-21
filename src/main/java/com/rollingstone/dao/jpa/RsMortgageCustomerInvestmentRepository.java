package com.rollingstone.dao.jpa;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rollingstone.domain.Customer;
import com.rollingstone.domain.Investment;



public interface RsMortgageCustomerInvestmentRepository extends PagingAndSortingRepository<Investment, Long> {
    List<Investment> findCustomerInvestmentsByCustomer(Customer customer);

    Page findAll(Pageable pageable);
}
