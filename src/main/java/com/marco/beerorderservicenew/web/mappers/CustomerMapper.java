package com.marco.beerorderservicenew.web.mappers;

import com.marco.beerorderservicenew.domain.Customer;
import com.marco.dtocommoninterface.model.order.CustomerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface CustomerMapper {
    Customer customerDtoToCustomer(CustomerDto dto);
    CustomerDto customerToCustomerDto(Customer customer);
}
