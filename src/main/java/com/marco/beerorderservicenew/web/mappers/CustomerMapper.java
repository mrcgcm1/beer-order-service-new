package com.marco.beerorderservicenew.web.mappers;

import com.marco.beerorderservicenew.domain.Customer;
import com.marco.dtocommoninterface.model.CustomerDto;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer customer);
}
