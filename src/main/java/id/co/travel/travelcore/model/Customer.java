package id.co.travel.travelcore.model;

import lombok.Data;

@Data
public class Customer {
    private int customerId;
    private String name;
    private String gender;
    private String email;
}
