package id.co.travel.travelcore.model;

import lombok.Data;

@Data
public class Notif {
    private String title;
    private String description;
    private String body;
    private String email;
}
