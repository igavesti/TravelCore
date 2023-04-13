package id.co.travel.travelcore.model;

import lombok.Data;

@Data
public class Card {
    private String cardNumber;
    private String cardName;
    private String expiredDate;
    private String cvv;
}
