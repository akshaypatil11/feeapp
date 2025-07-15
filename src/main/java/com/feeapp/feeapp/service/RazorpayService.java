package com.feeapp.feeapp.service;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class RazorpayService {

    @Value("${RAZORPAY_KEY:none}")
    private String key;
    @Value("${RAZORPAY_SECRET:none}")
    private String secret;

    public String createPaymentLink(String name, String phone, BigDecimal amount, String callbackUrl) {
        try {
            RazorpayClient client = new RazorpayClient(key, secret);
            JSONObject request = new JSONObject();
            request.put("amount", amount.multiply(new BigDecimal(100))); // in paise
            request.put("currency", "INR");
            request.put("description", name + " fees");
            request.put("customer", new JSONObject().put("contact", phone).put("name", name));
            request.put("notify", new JSONObject().put("sms", 1));
            request.put("callback_url", callbackUrl);
            request.put("callback_method", "get");
            PaymentLink link = client.paymentLink.create(request);
            return link.get("id");
        } catch (Exception e) {
            throw new RuntimeException("Razorpay error", e);
        }
    }
}
