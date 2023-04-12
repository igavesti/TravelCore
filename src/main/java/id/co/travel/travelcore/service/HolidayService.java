package id.co.travel.travelcore.service;

import id.co.travel.travelcore.repository.dao.PackageHolidayRepository;
import id.co.travel.travelcore.repository.model.PackageHoliday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService implements IHolidayService{
    private static final Logger LOGGER = LoggerFactory.getLogger(HolidayService.class);

    @Autowired
    private PackageHolidayRepository packageHolidayRepository;

    @Override
    public List<PackageHoliday> findAllPackages() {
        LOGGER.info("Find all packages");
        return packageHolidayRepository.findAll();
    }

    @Override
    public Optional<PackageHoliday> findPackagesById(int id) {
        LOGGER.info("Find packages by id");
        return packageHolidayRepository.findById(id);
    }
}
