package com.marco.beerorderservicenew.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marco.beerorderservicenew.config.JmsConfig;
import com.marco.beerorderservicenew.model.ValidateBeerOrderRequest;
import com.marco.dtocommoninterface.model.order.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

@RequiredArgsConstructor
@Component
public class NewOrderSenderImpl implements NewOrderSender{

    private final JmsTemplate jmsTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public void sendMessage(BeerOrderDto beerOrderDto){
    System.out.println("Manda un messaggio");
        ValidateBeerOrderRequest message = ValidateBeerOrderRequest.builder()
                .beerOrderDto(beerOrderDto)
                .build();

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_QUEUE, message);
        System.out.println("Message Sent!");

    }

    @Override
    public void sendAndReceiveMessage(BeerOrderDto beerOrderDto) throws JMSException {

        ValidateBeerOrderRequest message = ValidateBeerOrderRequest.builder()
                .beerOrderDto(beerOrderDto)
                .build();

        Message receivedMessage = jmsTemplate.sendAndReceive(JmsConfig.VALIDATE_ORDER_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {

                try {
                    Message helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                    helloMessage.setStringProperty("_type", "com.marco.jmsqueue.model.HelloWorldMessage");
                    System.out.println("Sending Message!");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException("Errore !");
                }
            }
        });
        System.out.println("Message Sent! :" + receivedMessage.getBody(String.class));

    }
}
