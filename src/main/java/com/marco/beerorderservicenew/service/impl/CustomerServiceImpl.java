package com.marco.beerorderservicenew.service.impl;

import com.marco.beerorderservicenew.domain.Customer;
import com.marco.beerorderservicenew.repositories.CustomerRepository;
import com.marco.beerorderservicenew.service.CustomerService;
import com.marco.beerorderservicenew.web.mappers.CustomerMapper;
import com.marco.dtocommoninterface.model.CustomerPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public CustomerPagedList listCustomers(PageRequest pageable) {
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return new CustomerPagedList(customerPage
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList()),
                PageRequest.of(customerPage.getPageable().getPageNumber(),
                        customerPage.getPageable().getPageSize()),
                customerPage.getTotalElements());
    }
}
