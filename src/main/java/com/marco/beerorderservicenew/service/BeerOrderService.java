package com.marco.beerorderservicenew.service;

import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BeerOrderService {
    BeerOrderPagedList listOrders(UUID customerId, Pageable pageable);

    BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto);

    BeerOrderDto getOrderById(UUID customerId, UUID orderId);

    BeerOrderLineDto getOrderLineById(UUID customerId, UUID orderLineId);


    void pickupOrder(UUID customerId, UUID orderId);

}
