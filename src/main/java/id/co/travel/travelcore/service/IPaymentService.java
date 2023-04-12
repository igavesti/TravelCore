package id.co.travel.travelcore.service;

import id.co.travel.travelcore.exception.CustomException;
import id.co.travel.travelcore.model.Payment;

public interface IPaymentService {
    String submit(Payment payment) throws CustomException;
}
