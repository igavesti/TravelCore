package id.co.travel.travelcore.model;

import lombok.Data;

@Data
public class Payment {
    private int customerId;
    private int packageId;
    private int paymentType;
    private Card card;
    private Crypto crypto;
    private CryptoCredential cryptoCredential;
}
