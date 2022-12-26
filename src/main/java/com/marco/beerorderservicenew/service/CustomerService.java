package com.marco.beerorderservicenew.service;

import com.marco.dtocommoninterface.model.CustomerPagedList;
import org.springframework.data.domain.PageRequest;

public interface CustomerService {
    CustomerPagedList listCustomers(PageRequest of);
}
