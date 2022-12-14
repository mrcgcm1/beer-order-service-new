package com.marco.beerorderservicenew.domain;

public enum BeerOrderEventEnum {
    NEW,
    VALIDATE_ORDER,
    VALIDATION_PASSED,
    VALIDATION_FAILED,
    ALLOCATION_SUCCESS,
    ALLOCATION_ON_INVENTORY,
    ALLOCATION_FAILED,
    BEER_ORDER_PICKED_UP,
    BEER_ORDER_DELIVERED
}
