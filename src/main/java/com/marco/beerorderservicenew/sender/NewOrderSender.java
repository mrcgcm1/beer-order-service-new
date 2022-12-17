package com.marco.beerorderservicenew.sender;

import com.marco.dtocommoninterface.model.order.BeerOrderDto;

import javax.jms.JMSException;

public interface NewOrderSender {
    void sendMessage(BeerOrderDto beerOrderDto);

    void sendAndReceiveMessage(BeerOrderDto beerOrderDto) throws JMSException;
}
