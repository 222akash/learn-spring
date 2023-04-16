package learnspring.paymentService.service;

import learnspring.paymentService.model.PaymentRequest;
import org.springframework.http.HttpStatus;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

}
