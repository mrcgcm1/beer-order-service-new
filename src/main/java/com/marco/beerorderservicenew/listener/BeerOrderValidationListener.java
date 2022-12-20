package com.marco.beerorderservicenew.listener;

import com.marco.beerorderservicenew.service.BeerOrderManager;
import com.marco.dtocommoninterface.config.JmsConfig;
import com.marco.dtocommoninterface.model.order.ValidateBeerOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final BeerOrderManager beerOrderManager;


    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE_RESULT)
    public void listenForHello(@Payload ValidateBeerOrderResponse response, @Headers MessageHeaders headers, Message message) throws JMSException {

        System.out.println("Ricevuto il messaggio dalla coda " + JmsConfig.VALIDATE_ORDER_QUEUE);

        beerOrderManager.processValidationResult(response.getOrderId(), response.isResult());

    }


}
