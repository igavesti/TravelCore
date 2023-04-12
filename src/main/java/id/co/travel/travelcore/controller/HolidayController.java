package id.co.travel.travelcore.controller;

import id.co.travel.travelcore.service.IHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/holiday")
public class HolidayController {
    @Autowired
    private IHolidayService iHolidayService;

    @GetMapping("/list-packages")
    public ResponseEntity<Object> listPackage() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(iHolidayService.findAllPackages());
    }
}
