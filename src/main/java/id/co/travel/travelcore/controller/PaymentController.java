package id.co.travel.travelcore.controller;

import id.co.travel.travelcore.exception.CustomException;
import id.co.travel.travelcore.model.Payment;
import id.co.travel.travelcore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/list-packages")
    public ResponseEntity<Object> submit(@RequestBody Payment payment) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(paymentService.submit(payment));
        } catch (CustomException e) {
            //Failed
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getErrorMessage());
        }
    }
}
