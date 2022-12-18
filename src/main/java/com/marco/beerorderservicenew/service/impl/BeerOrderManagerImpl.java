package com.marco.beerorderservicenew.service.impl;

import com.marco.beerorderservicenew.domain.BeerOrder;
import com.marco.beerorderservicenew.domain.BeerOrderEventEnum;
import com.marco.beerorderservicenew.domain.BeerOrderStatusEnum;
import com.marco.beerorderservicenew.repositories.BeerOrderRepository;
import com.marco.beerorderservicenew.service.BeerOrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerOrderManagerImpl implements BeerOrderManager {

    public static final String BEER_ORDER_ID_HEADER = "bo_id";
    private final StateMachineFactory<BeerOrderStatusEnum, BeerOrderEventEnum> stateMachineFactory;

    private final BeerOrderRepository beerOrderRepository;

    @Transactional
    @Override
    public BeerOrder newBeerOrder(BeerOrder beerOrder) {

        beerOrder.setId(null);
        beerOrder.setOrderStatus(BeerOrderStatusEnum.NEW);

        return beerOrderRepository.save(beerOrder);
    }

    @Override
    public void processValidationResult(UUID orderId, boolean isValid) {
        BeerOrder beerOrder = beerOrderRepository.findOneById(orderId);

        if (isValid) {
            sendEvent(beerOrder, BeerOrderEventEnum.VALIDATION_PASSED);
            BeerOrder validateOrder = beerOrderRepository.findOneById(orderId);
            sendEvent(validateOrder, BeerOrderEventEnum.ALLOCATE_ORDER);

        }else{
            sendEvent(beerOrder, BeerOrderEventEnum.VALIDATION_FAILED);
        }

    }

    private void sendEvent(BeerOrder beerOrder, BeerOrderEventEnum validationPassed) {
        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = build(beerOrder);
        Message msg = MessageBuilder.withPayload(validationPassed).build();
        sm.sendEvent(msg);
    }

    private void saveBeerOrderevent(BeerOrder beerOrder, BeerOrderEventEnum eventEnum) {
        sendEvent(beerOrder, eventEnum);
    }

    private StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> build(BeerOrder beerOrder) {

        StateMachine<BeerOrderStatusEnum, BeerOrderEventEnum> sm = stateMachineFactory.getStateMachine(beerOrder.getId());

        sm.stop();

        sm.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.resetStateMachine(new DefaultStateMachineContext<>(beerOrder.getOrderStatus(), null, null, null));
        });

        sm.start();

        return sm;
    }
}
