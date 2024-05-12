package com.eside.payment.controller;

import com.eside.payment.dto.Flouci.ResponsePayment;
import com.eside.payment.exception.InvalidOperationException;
import com.eside.payment.service.impl.PaymentServiceImpl;
import com.eside.payment.utils.SuccessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@RequestMapping("/api/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentServiceImpl payementService;

    @GetMapping("/payment/success/{paymentId}")
    public SuccessDto paymentSuccess(@PathVariable String paymentId) throws IOException {
        boolean verifPayment = payementService.verifyPayment(paymentId);
        if (verifPayment) {
            return SuccessDto.builder()
                    .message("Transaction succesfuly created")
                    .build();
        }else{
            throw new InvalidOperationException("payment not valid !");
        }
    }

//    @GetMapping("/")
//    public String index() {
//        return "index";
//    }


    @PostMapping("/payment/create/{amount}")
    public ResponsePayment createPayment(@PathVariable Integer amount) throws IOException {
        return payementService.generatePayment(amount);
    }
}