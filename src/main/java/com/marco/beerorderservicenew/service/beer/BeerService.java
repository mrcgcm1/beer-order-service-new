package com.marco.beerorderservicenew.service.beer;

import com.marco.dtocommoninterface.model.BeerDto;

import java.util.Optional;
import java.util.UUID;

public interface BeerService {
    Optional<BeerDto> getBeerById(UUID uuid);

    Optional<BeerDto> getBeerByUpc(String upc);
}
