package com.marco.beerorderservicenew.service;

import com.marco.beerorderservicenew.domain.BeerOrder;
import com.marco.beerorderservicenew.domain.Customer;
import com.marco.beerorderservicenew.domain.OrderStatusEnum;
import com.marco.beerorderservicenew.repositories.BeerOrderRepository;
import com.marco.beerorderservicenew.repositories.CustomerRepository;
import com.marco.beerorderservicenew.web.mappers.BeerOrderMapper;
import com.marco.beerorderservicenew.web.model.BeerOrderDto;
import com.marco.beerorderservicenew.web.model.BeerOrderLineDto;
import com.marco.beerorderservicenew.web.model.BeerOrderPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class BeerOrderServiceImpl implements BeerOrderService {

    private final BeerOrderRepository beerOrderRepository;
    private final CustomerRepository customerRepository;
    private final BeerOrderMapper beerOrderMapper;
    private final ApplicationEventPublisher publisher;

    public BeerOrderServiceImpl(BeerOrderRepository beerOrderRepository,
                                CustomerRepository customerRepository,
                                BeerOrderMapper beerOrderMapper, ApplicationEventPublisher publisher) {
        this.beerOrderRepository = beerOrderRepository;
        this.customerRepository = customerRepository;
        this.beerOrderMapper = beerOrderMapper;
        this.publisher = publisher;
    }

    @Override
    public BeerOrderPagedList listOrders(UUID customerId, Pageable pageable) {

        return null;
    }

    @Override
    public BeerOrderDto placeOrder(UUID customerId, BeerOrderDto beerOrderDto) {

        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            BeerOrder beerOrder = beerOrderMapper.dtoToBeerOrder(beerOrderDto);
            beerOrder.setId(null); //should not be set by outside client
            beerOrder.setCustomer(customerOptional.get());
            beerOrder.setOrderStatus(OrderStatusEnum.NEW);

            // TODO
            beerOrder.getBeerOrderLines().forEach(line -> {

                line.setBeerOrder(beerOrder);
            });

            BeerOrder savedBeerOrder = beerOrderRepository.saveAndFlush(beerOrder);

            log.debug("Saved Beer Order: " + beerOrder.getId());

            //todo impl
            //  publisher.publishEvent(new NewBeerOrderEvent(savedBeerOrder));

            return beerOrderMapper.beerOrderToDto(savedBeerOrder);
        }
        //todo add exception type
        throw new RuntimeException("Customer Not Found");
    }

    @Override
    public BeerOrderDto getOrderById(UUID customerId, UUID orderId) {
        return beerOrderMapper.beerOrderToDto(getOrder(customerId, orderId));
    }

    @Override
    public BeerOrderLineDto getOrderLineById(UUID customerId, UUID orderLineId) {
        return null;
    }

    @Override
    public void pickupOrder(UUID customerId, UUID orderId) {
        BeerOrder beerOrder = getOrder(customerId, orderId);
        beerOrder.setOrderStatus(OrderStatusEnum.PICKED_UP);

        beerOrderRepository.save(beerOrder);
    }

    private BeerOrder getOrder(UUID customerId, UUID orderId) {
        Optional<Customer> customerOptional = customerRepository.findById(customerId);

        if (customerOptional.isPresent()) {
            Optional<BeerOrder> beerOrderOptional = beerOrderRepository.findById(orderId);

            if (beerOrderOptional.isPresent()) {
                BeerOrder beerOrder = beerOrderOptional.get();

                // fall to exception if customer id's do not match - order not for customer
                if (beerOrder.getCustomer().getId().equals(customerId)) {
                    return beerOrder;
                }
            }
            throw new RuntimeException("Beer Order Not Found");
        }
        throw new RuntimeException("Customer Not Found");
    }
}

