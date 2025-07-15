package com.feeapp.feeapp.service;

import com.feeapp.feeapp.entity.Fee;
import com.feeapp.feeapp.entity.Payment;
import com.feeapp.feeapp.repository.FeeRepository;
import com.feeapp.feeapp.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final FeeRepository feeRepository;
    private final PaymentRepository paymentRepository;
    private final RazorpayService razorpayService;
    private final TwilioService twilioService;

    @Value("${APP_BASE_URL:http://localhost:8080}")
    private String baseUrl;

    public void sendPaymentLink(Fee fee) {
        String callback = baseUrl + "/api/payment/callback";
        String linkId = razorpayService.createPaymentLink(
                fee.getStudent().getName(),
                fee.getStudent().getPhone(),
                fee.getAmount(),
                callback
        );
        Payment payment = Payment.builder()
                .fee(fee)
                .linkId(linkId)
                .status("PENDING")
                .build();
        paymentRepository.save(payment);
        String message = "Please pay fees: https://rzp.io/i/" + linkId;
        twilioService.sendSms(fee.getStudent().getPhone(), message);
    }

    public void markPaid(String linkId) {
        Payment payment = paymentRepository.findByLinkId(linkId);
        if (payment != null) {
            payment.setStatus("PAID");
            paymentRepository.save(payment);
            Fee fee = payment.getFee();
            fee.setPaid(true);
            feeRepository.save(fee);
        }
    }
}
