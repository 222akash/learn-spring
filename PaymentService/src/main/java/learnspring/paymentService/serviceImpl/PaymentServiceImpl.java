package learnspring.paymentService.serviceImpl;

import learnspring.paymentService.entity.TransactionDetails;
import learnspring.paymentService.model.PaymentRequest;
import learnspring.paymentService.repository.TransactionDetailsRepository;
import learnspring.paymentService.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Instant;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails =
                TransactionDetails.builder()
                        .paymentMode(paymentRequest.getPaymentMode().name())
                        .referenceNumber(paymentRequest.getRefrenceNumber())
                        .amount(paymentRequest.getAmount())
                        .orderId(paymentRequest.getOrderId())
                        .paymentDate(Instant.now())
                        .paymentStatus("SUCCESS")
                        .build();

        transactionDetailsRepository.save(transactionDetails);
        log.info("Transaction Completed with transactionId: {}", transactionDetails.getId());
        return transactionDetails.getId();
    }
}
