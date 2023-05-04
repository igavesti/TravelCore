package id.co.travel.travelcore.model;

import lombok.Data;

import java.math.BigInteger;

@Data
public class CryptoPaymentDetailCurrency {
    private int id;
    private String name;
    private String symbol;
    private BigInteger rate;
}
