package com.marco.beerorderservicenew.domain;

public enum BeerOrderStatusEnum {
    NEW,
    VALIDATED,
    VALIDATION_PENDING,
    VALIDATED_EXCEPTION,
    ALLOCATED,
    ALLOCATION_PENDING,
    ALLOCATED_EXCEPTION,
    PENDING_INVENTORY,
    READY,
    PICKED_UP,
    DELIVERED,
    DELIVERY_EXCEPTION
}
