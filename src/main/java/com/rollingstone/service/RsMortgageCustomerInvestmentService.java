package com.rollingstone.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.rollingstone.dao.jpa.RsMortgageCustomerInvestmentRepository;
import com.rollingstone.domain.Investment;
import com.rollingstone.domain.Customer;


/*
 * Service class to do CRUD for Customer Investment through JPS Repository
 */
@Service
public class RsMortgageCustomerInvestmentService {

    private static final Logger log = LoggerFactory.getLogger(RsMortgageCustomerInvestmentService.class);

    @Autowired
    private RsMortgageCustomerInvestmentRepository customerInvestmentRepository;

    @Autowired
    CounterService counterService;

    @Autowired
    GaugeService gaugeService;

    public RsMortgageCustomerInvestmentService() {
    }

    public Investment createInvestment(Investment investment) {
        return customerInvestmentRepository.save(investment);
    }

    public Investment getInvestment(long id) {
        return customerInvestmentRepository.findOne(id);
    }

    public void updateInvestment(Investment investment) {
    	customerInvestmentRepository.save(investment);
    }

    public void deleteInvestment(Long id) {
    	customerInvestmentRepository.delete(id);
    }

    public Page<Investment> getAllInvestmentsByPage(Integer page, Integer size) {
        Page pageOfInvestments = customerInvestmentRepository.findAll(new PageRequest(page, size));
        
        // example of adding to the /metrics
        if (size > 50) {
            counterService.increment("com.rollingstone.getAll.largePayload");
        }
        return pageOfInvestments;
    }
    
    public List<Investment> getAllInvestments() {
        Iterable<Investment> pageOfInvestments = customerInvestmentRepository.findAll();
        
        List<Investment> customerInvestments = new ArrayList<Investment>();
        
        for (Investment investment : pageOfInvestments){
        	customerInvestments.add(investment);
        }
    	log.info("In Real Service getAllInvestments  size :"+customerInvestments.size());

    	
        return customerInvestments;
    }
    
    public List<Investment> getAllInvestmentsForCustomer(Customer customer) {
        Iterable<Investment> pageOfInvestments = customerInvestmentRepository.findCustomerInvestmentsByCustomer(customer);
        
        List<Investment> customerInvestments = new ArrayList<Investment>();
        
        for (Investment investment : pageOfInvestments){
        	customerInvestments.add(investment);
        }
    	log.info("In Real Service getAllInvestments  size :"+customerInvestments.size());

    	
        return customerInvestments;
    }
}
