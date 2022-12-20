package com.marco.beerorderservicenew.sm.actions;

import com.marco.beerorderservicenew.domain.BeerOrderEventEnum;
import com.marco.beerorderservicenew.domain.BeerOrderStatusEnum;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class AllocationFailureAction  implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {

    }
}
