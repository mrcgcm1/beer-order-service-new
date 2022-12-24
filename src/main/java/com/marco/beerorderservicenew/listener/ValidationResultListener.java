package com.marco.beerorderservicenew.listener;

import com.marco.beerorderservicenew.service.BeerOrderManager;
import com.marco.dtocommoninterface.config.JmsConfig;
import com.marco.dtocommoninterface.model.order.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class ValidationResultListener {

    private final BeerOrderManager beerOrderManager;


    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE)
    public void listen(@Payload ValidateOrderResult result, @Headers MessageHeaders headers, Message message) throws JMSException {
        final UUID beerOrderId = result.getOrderId();

        System.out.println("Risultato della validazione  per Order id " + beerOrderId);

        beerOrderManager.processValidationResult(result.getOrderId(), result.isValid());

    }


}
