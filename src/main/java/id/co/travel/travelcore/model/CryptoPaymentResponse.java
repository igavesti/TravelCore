package id.co.travel.travelcore.model;

import lombok.Data;

@Data
public class CryptoPaymentResponse {
    private boolean status;
    private String message;
    private String balance;
    private CryptoPaymentDetail data;
}
