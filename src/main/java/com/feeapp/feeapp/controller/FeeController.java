package com.feeapp.feeapp.controller;

import com.feeapp.feeapp.entity.Fee;
import com.feeapp.feeapp.repository.FeeRepository;
import com.feeapp.feeapp.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fee")
@RequiredArgsConstructor
public class FeeController {

    private final FeeRepository feeRepository;
    private final PaymentService paymentService;

    @PostMapping("/remind/{id}")
    public ResponseEntity<String> remind(@PathVariable Long id) {
        Fee fee = feeRepository.findById(id).orElseThrow();
        paymentService.sendPaymentLink(fee);
        return ResponseEntity.ok("Reminder sent");
    }
}
