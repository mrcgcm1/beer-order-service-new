package com.marco.beerorderservicenew.listener;

import com.marco.beerorderservicenew.service.BeerOrderManager;
import com.marco.dtocommoninterface.config.JmsConfig;
import com.marco.dtocommoninterface.model.order.DeallocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderDeallocationResultListener {

    private final BeerOrderManager beerOrderManager;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_RESPONSE_QUEUE)
    public void listen(DeallocateOrderResult result){
        beerOrderManager.beerOrderDeallocationPassed(result.getBeerOrderDto());
    }


}
