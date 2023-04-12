package id.co.travel.travelcore.repository.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "package_holiday")
@Data
public class PackageHoliday {
    @Id
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
}
