package com.marco.beerorderservicenew.service;

import com.marco.beerorderservicenew.domain.BeerOrder;
import com.marco.beerorderservicenew.domain.BeerOrderEventEnum;
import com.marco.beerorderservicenew.domain.BeerOrderStatusEnum;
import com.marco.beerorderservicenew.repositories.BeerOrderRepository;
import com.marco.beerorderservicenew.service.impl.BeerOrderManagerImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BeerOrderStateChangeInterceptor extends StateMachineInterceptorAdapter<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    @Override
    public void preStateChange(State<BeerOrderStatusEnum,
            BeerOrderEventEnum> state,
                               Message<BeerOrderEventEnum> message,
                               Transition<BeerOrderStatusEnum, BeerOrderEventEnum> transition,
                               StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachine) {

        Optional.ofNullable(message).ifPresent(msg ->{
            Optional.ofNullable(UUID.class.cast(msg.getHeaders().getOrDefault(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER, -1L))).ifPresent(paymentId ->{
                BeerOrder payment = beerOrderRepository.findById(paymentId).orElse(null);
                if(payment != null){
                    payment.setOrderStatus(state.getId());
                    beerOrderRepository.save(payment);
                }
            });
        });
    }
}
