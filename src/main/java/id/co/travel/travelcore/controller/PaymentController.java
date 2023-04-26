package id.co.travel.travelcore.controller;

import id.co.travel.travelcore.exception.CustomException;
import id.co.travel.travelcore.model.Payment;
import id.co.travel.travelcore.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/submit")
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
        } catch (Exception e) {
            LOGGER.error("Exception " + payment.getCustomerId() + " : ", e);
            //Failed
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Sementara transaksi tidak dapat dilakukan");
        }
    }
}
