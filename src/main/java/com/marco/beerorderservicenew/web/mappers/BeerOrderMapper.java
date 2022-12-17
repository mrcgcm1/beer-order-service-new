package com.marco.beerorderservicenew.web.mappers;

import com.marco.beerorderservicenew.domain.BeerOrder;
import com.marco.dtocommoninterface.model.order.BeerOrderDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class, BeerOrderLineMapper.class})
public interface BeerOrderMapper {
    BeerOrderDto beerOrderToDto(BeerOrder beerOrder);

    BeerOrder dtoToBeerOrder(BeerOrderDto dto);

}
