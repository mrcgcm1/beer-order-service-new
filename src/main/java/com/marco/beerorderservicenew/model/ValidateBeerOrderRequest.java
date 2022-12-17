package com.marco.beerorderservicenew.model;

import com.marco.dtocommoninterface.model.order.BeerOrderDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidateBeerOrderRequest {
    static final long serialVersionUID = -3823394930387935997L;

    private BeerOrderDto beerOrderDto;

}
