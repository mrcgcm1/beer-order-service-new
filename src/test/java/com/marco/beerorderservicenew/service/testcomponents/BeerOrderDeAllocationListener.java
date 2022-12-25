package com.marco.beerorderservicenew.service.testcomponents;

import com.marco.dtocommoninterface.config.JmsConfig;
import com.marco.dtocommoninterface.model.order.DeallocateOrderRequest;
import com.marco.dtocommoninterface.model.order.DeallocateOrderResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderDeAllocationListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.DEALLOCATE_ORDER_QUEUE)
    public void listen(Message msg){
        DeallocateOrderRequest request = (DeallocateOrderRequest) msg.getPayload();
        boolean pendingInventory = false;
   
        boolean finalPendingInventory = pendingInventory;

        request.getBeerOrderDto().getBeerOrderLines().forEach(beerOrderLineDto -> {
            if (finalPendingInventory) {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity() - 1);
            } else {
                beerOrderLineDto.setQuantityAllocated(beerOrderLineDto.getOrderQuantity());
            }
        });

            jmsTemplate.convertAndSend(JmsConfig.DEALLOCATE_ORDER_RESPONSE_QUEUE,
                    DeallocateOrderResult.builder()
                            .beerOrderDto(request.getBeerOrderDto())
                            .build());
    }
}
