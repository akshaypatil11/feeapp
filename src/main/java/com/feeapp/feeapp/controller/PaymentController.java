package com.feeapp.feeapp.controller;

import com.feeapp.feeapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/callback")
    public ResponseEntity<String> callback(@RequestParam("linkId") String linkId) {
        paymentService.markPaid(linkId);
        return ResponseEntity.ok("Payment updated");
    }
}
