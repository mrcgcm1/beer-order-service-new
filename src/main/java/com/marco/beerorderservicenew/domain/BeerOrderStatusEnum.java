package com.marco.beerorderservicenew.domain;

public enum BeerOrderStatusEnum {
    NEW,
    VALIDATED,
    VALIDATED_EXCEPTION,
    ALLOCATED,
    ALLOCATED_EXCEPTION,
    PENDING_INVENTORY,
    READY,
    PICKED_UP,
    DELIVERED,
    DELIVERY_EXCEPTION
}
