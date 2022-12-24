package com.marco.beerorderservicenew.sm.actions;

import com.marco.beerorderservicenew.domain.BeerOrderEventEnum;
import com.marco.beerorderservicenew.domain.BeerOrderStatusEnum;
import com.marco.beerorderservicenew.service.impl.BeerOrderManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ValidationFailureAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER);
        log.error("Compensating Transaction.... Validation Failed: " + beerOrderId);
    }
}
