package id.co.travel.travelcore.service;

import id.co.travel.travelcore.config.ApplicationProperties;
import id.co.travel.travelcore.exception.CustomException;
import id.co.travel.travelcore.model.Card;
import id.co.travel.travelcore.model.Customer;
import id.co.travel.travelcore.model.Payment;
import id.co.travel.travelcore.repository.model.PackageHoliday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class PaymentService implements IPaymentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentService.class);
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private ApplicationProperties applicationProperties;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String submit(Payment payment) throws CustomException {
        LOGGER.info("Submit payment");

        LOGGER.info("Get data Customer");
        Customer customer = getDataCustomer(payment.getCustomerId());

        LOGGER.info("Vefify Packages is available");
        Optional<PackageHoliday> optionalPackageHoliday = holidayService.findPackagesById(payment.getPackageId());
        if (optionalPackageHoliday.isEmpty()) {
            throw new CustomException("Packages is not available");
        }

        LOGGER.info("Verification Payment");
        dummyVerificationPayment(payment.getCard());

        LOGGER.info("Put Notif Email Kafka");
        //TODO put kafka

        LOGGER.info("Success");
        return "Success";
    }

    private Customer getDataCustomer(int customerId) throws CustomException {
        try {
            ResponseEntity<Customer> response
                    = restTemplate.getForEntity(applicationProperties.getUrlGetDataCustomer() + "/" + customerId, Customer.class);
            return response.getBody();
        } catch (Exception e) {
            throw new CustomException(e.getMessage());
        }
    }

    private void dummyVerificationPayment(Card card) throws CustomException {
        //Dummy Call Service Verification Payment;
        if (!applicationProperties.getDummyServiceResponse().equals("OK")) {
            throw new CustomException("Payment Failed");
        }
    }
}
