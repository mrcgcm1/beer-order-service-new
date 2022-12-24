package com.marco.beerorderservicenew.service.testcomponents;

import com.marco.dtocommoninterface.config.JmsConfig;
import com.marco.dtocommoninterface.model.order.ValidateOrderRequest;
import com.marco.dtocommoninterface.model.order.ValidateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE)
    public void list(Message msg){

        boolean isValid = true;
        boolean sendResponse = true;

        ValidateOrderRequest request = (ValidateOrderRequest) msg.getPayload();

        //condition to fail validation
        if (request.getBeerOrder().getCustomerRef() != null) {
            if (request.getBeerOrder().getCustomerRef().equals("fail-validation")){
                isValid = false;
            } else if (request.getBeerOrder().getCustomerRef().equals("dont-validate")){
                sendResponse = false;
            }
        }

        if (sendResponse) {
            ValidateOrderResult result = ValidateOrderResult.builder()
                    .isValid(isValid)
                    .orderId(request.getBeerOrder().getId())
                    .build();
            jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESPONSE_QUEUE,
                    result);

        }


//        ValidateBeerOrderRequest request = (ValidateBeerOrderRequest) msg.getPayload();
//
//        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE_RESULT,
//                ValidateBeerOrderResponse.builder().result(true).orderId(request.getBeerOrderDto().getId()));
//


    }

}
