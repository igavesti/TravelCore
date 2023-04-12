package id.co.travel.travelcore.repository.dao;

import id.co.travel.travelcore.repository.model.PackageHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackageHolidayRepository extends JpaRepository<PackageHoliday, Integer> {
}
