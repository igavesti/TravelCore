package id.co.travel.travelcore.repository.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "package_holiday")
@Data
public class PackageHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "package_name")
    private String packageName;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "rating")
    private String rating;
    @Column(name = "quota_left")
    private int quotaLeft;
}
