package com.marco.beerorderservicenew.service;

import com.marco.beerorderservicenew.domain.BeerOrder;
import com.marco.dtocommoninterface.model.order.BeerOrderDto;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID orderId, boolean result);
    void beerOrderAllocationPassed(BeerOrderDto beerOrderDto);

    void beerOrderAllocationPendingInventory(BeerOrderDto beerOrderDto);

    void beerOrderAllocationFailed(BeerOrderDto beerOrderDto);

    void beerOrderPickedUp(UUID orderId);

    void cancelOrder(UUID id);
}
