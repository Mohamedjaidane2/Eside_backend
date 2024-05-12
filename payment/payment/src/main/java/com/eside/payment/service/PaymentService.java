package com.eside.payment.service;

import com.eside.payment.dto.Flouci.ResponsePayment;

import java.io.IOException;

public interface PaymentService {
    public ResponsePayment generatePayment(Integer amount) throws IOException;
    public boolean verifyPayment(String paymentId) throws IOException;
}
