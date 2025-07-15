package com.feeapp.feeapp.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    @Value("${TWILIO_SID:none}")
    private String sid;
    @Value("${TWILIO_AUTH:none}")
    private String auth;
    @Value("${TWILIO_NUMBER:+10000000000}")
    private String from;

    private void init() {
        Twilio.init(sid, auth);
    }

    public void sendSms(String to, String body) {
        init();
        Message.creator(new PhoneNumber(to), new PhoneNumber(from), body).create();
    }
}
