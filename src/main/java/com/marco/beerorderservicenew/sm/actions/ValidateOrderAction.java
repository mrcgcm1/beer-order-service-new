package com.marco.beerorderservicenew.sm.actions;

import com.marco.beerorderservicenew.config.JmsConfig;
import com.marco.beerorderservicenew.domain.BeerOrder;
import com.marco.beerorderservicenew.domain.BeerOrderEventEnum;
import com.marco.beerorderservicenew.domain.BeerOrderStatusEnum;
import com.marco.beerorderservicenew.model.ValidateBeerOrderRequest;
import com.marco.beerorderservicenew.repositories.BeerOrderRepository;
import com.marco.beerorderservicenew.service.impl.BeerOrderManagerImpl;
import com.marco.beerorderservicenew.web.mappers.BeerOrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class ValidateOrderAction implements Action<BeerOrderStatusEnum, BeerOrderEventEnum> {

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final JmsTemplate jmsTemplate;
    @Override
    public void execute(StateContext<BeerOrderStatusEnum, BeerOrderEventEnum> context) {
        String beerOrderId = (String) context.getMessage().getHeaders().get(BeerOrderManagerImpl.BEER_ORDER_ID_HEADER);

        Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(UUID.fromString(beerOrderId));

        beerOrderOptional.ifPresentOrElse(beerOrder -> {
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, ValidateBeerOrderRequest.builder()
                    .beerOrderDto(beerOrderMapper.beerOrderToDto(beerOrder))
                    .build());
        }, () -> log.error("Order Not Found. Id: " + beerOrderId));

        log.debug("Sent Validation request to queue for order id " + beerOrderId);

    }
}
