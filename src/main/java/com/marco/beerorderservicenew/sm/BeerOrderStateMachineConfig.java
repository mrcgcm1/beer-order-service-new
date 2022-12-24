package com.marco.beerorderservicenew.sm;

import com.marco.beerorderservicenew.domain.BeerOrderEventEnum;
import com.marco.beerorderservicenew.domain.BeerOrderStatusEnum;
import com.marco.beerorderservicenew.sm.actions.AllocationOrderAction;
import com.marco.beerorderservicenew.sm.actions.AllocationFailureAction;
import com.marco.beerorderservicenew.sm.actions.DeallocationOrderAction;
import com.marco.beerorderservicenew.sm.actions.ValidateOrderAction;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Slf4j
@Component
@RequiredArgsConstructor
@EnableStateMachineFactory
public class BeerOrderStateMachineConfig extends StateMachineConfigurerAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final ValidateOrderAction validateOrderAction;
    private final AllocationOrderAction allocateOrderAction;
    private final AllocationFailureAction allocationFailureAction;
    private final DeallocationOrderAction deallocateOrderAction;

    private final Action<BeerOrderStatusEnum, BeerOrderEventEnum> validationFailureAction;

    @Override
    public void configure(StateMachineStateConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> states) throws Exception {
        states.withStates()
                .initial(BeerOrderStatusEnum.NEW)
                .states(EnumSet.allOf(BeerOrderStatusEnum.class))
                .end(BeerOrderStatusEnum.PICKED_UP)
                .end(BeerOrderStatusEnum.DELIVERED)
                .end(BeerOrderStatusEnum.DELIVERY_EXCEPTION)
                .end(BeerOrderStatusEnum.VALIDATED_EXCEPTION)
                .end(BeerOrderStatusEnum.ALLOCATED_EXCEPTION);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<BeerOrderStatusEnum, BeerOrderEventEnum> transitions) throws Exception {
        transitions
                .withExternal().source(BeerOrderStatusEnum.NEW).target(BeerOrderStatusEnum.VALIDATION_PENDING)
                .event(BeerOrderEventEnum.VALIDATE_ORDER)
                .action(validateOrderAction)
                .and()
                .withExternal().source(BeerOrderStatusEnum.VALIDATION_PENDING).target(BeerOrderStatusEnum.VALIDATED)
                .event(BeerOrderEventEnum.VALIDATION_PASSED)
                .and().withExternal()
                .source(BeerOrderStatusEnum.VALIDATION_PENDING).target(BeerOrderStatusEnum.CANCELLED)
                .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and()
                .withExternal().source(BeerOrderStatusEnum.VALIDATION_PENDING).target(BeerOrderStatusEnum.VALIDATED_EXCEPTION)
                .event(BeerOrderEventEnum.VALIDATION_FAILED).action(validationFailureAction)
                .and()
                .withExternal().source(BeerOrderStatusEnum.VALIDATED).target(BeerOrderStatusEnum.ALLOCATION_PENDING)
                .event(BeerOrderEventEnum.ALLOCATE_ORDER).action(allocateOrderAction)
                .and()
                .withExternal()
                .source(BeerOrderStatusEnum.VALIDATED).target(BeerOrderStatusEnum.CANCELLED)
                .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and()
                .withExternal().source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.ALLOCATED)
                .event(BeerOrderEventEnum.ALLOCATION_SUCCESS)
                .and()
                .withExternal().source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.ALLOCATED_EXCEPTION)
                .event(BeerOrderEventEnum.ALLOCATION_FAILED).action(allocationFailureAction)
                .and().withExternal()
                .source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.CANCELLED)
                .event(BeerOrderEventEnum.CANCEL_ORDER)
                .and()
                .withExternal().source(BeerOrderStatusEnum.ALLOCATION_PENDING).target(BeerOrderStatusEnum.PENDING_INVENTORY)
                .event(BeerOrderEventEnum.ALLOCATION_NO_INVENTORY)
                .and()
                .withExternal().source(BeerOrderStatusEnum.ALLOCATED).target(BeerOrderStatusEnum.PICKED_UP)
                .event(BeerOrderEventEnum.BEER_ORDER_PICKED_UP)
                .and().withExternal()
                .source(BeerOrderStatusEnum.ALLOCATED).target(BeerOrderStatusEnum.CANCELLED)
                .event(BeerOrderEventEnum.CANCEL_ORDER)
                .action(deallocateOrderAction);

    }
}
