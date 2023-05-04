package id.co.travel.travelcore.model;

import lombok.Data;

@Data
public class CryptoPaymentDetail {
    private int id;
    private CryptoPaymentDetailCurrency currency;
    private String balance;
}
