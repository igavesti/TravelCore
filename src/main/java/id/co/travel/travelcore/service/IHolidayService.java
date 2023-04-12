package id.co.travel.travelcore.service;

import id.co.travel.travelcore.repository.model.PackageHoliday;

import java.util.List;
import java.util.Optional;

public interface IHolidayService {
    List<PackageHoliday> findAllPackages();

    Optional<PackageHoliday> findPackagesById(int id);
}
