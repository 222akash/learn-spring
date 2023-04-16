package com.learnspring.orderservice.serviceImpl;

import com.learnspring.orderservice.entity.Order;
import com.learnspring.orderservice.external.client.PaymentService;
import com.learnspring.orderservice.external.client.ProductService;
import com.learnspring.orderservice.external.request.PaymentRequest;
import com.learnspring.orderservice.model.OrderRequest;
import com.learnspring.orderservice.repository.OrderRepository;
import com.learnspring.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private PaymentService paymentService;

    public Long placeOrder(OrderRequest orderRequest) {
        log.info("Placing Order Request: {}", orderRequest);

        //Product Service -> Block Products(Reduce Quantity)
        productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());

        log.info("Creating Order with Status  CREATED");

        //OrderEntity -> Save the data with Status Order created
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .productId(orderRequest.getProductId())
                .build();
        order = orderRepository.save(order);
        log.info("Calling Payment Service to complete the Payment");
        //Payment Service->Payments  ->Success -> Complete
        PaymentRequest paymentRequest =
                PaymentRequest.builder()
                        .orderId(order.getId())
                        .paymentMode(orderRequest.getPaymentMode())
                        .amount(orderRequest.getTotalAmount())
                        .build();
        String orderStatus = null;
        try {
            paymentService.doPayment(paymentRequest);
            log.info("Payment done Successfully, Changing Order Status..");
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in Payment ,Changing Order Status to Failed");
            orderStatus = "FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);

        //Cancel
        log.info("Order Successfully placed with orderId {} ", order.getId());
        return order.getId();
    }
}
