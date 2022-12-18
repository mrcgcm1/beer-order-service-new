package com.marco.beerorderservicenew.service;

import com.marco.beerorderservicenew.domain.BeerOrder;

import java.util.UUID;

public interface BeerOrderManager {

    BeerOrder newBeerOrder(BeerOrder beerOrder);

    void processValidationResult(UUID orderId, boolean result);
}
