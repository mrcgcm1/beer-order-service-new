package com.marco.beerorderservicenew.web.mappers;

import com.marco.beerorderservicenew.domain.BeerOrderLine;
import com.marco.dtocommoninterface.model.order.BeerOrderLineDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerOrderLineMapper {
    BeerOrderLineDto beerOrderLineToDto(BeerOrderLine line);

    BeerOrderLine dtoToBeerOrderLine(BeerOrderLineDto dto);
}
