package id.co.travel.travelcore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.travel.travelcore.config.ApplicationProperties;
import id.co.travel.travelcore.exception.CustomException;
import id.co.travel.travelcore.model.Card;
import id.co.travel.travelcore.model.Customer;
import id.co.travel.travelcore.model.Notif;
import id.co.travel.travelcore.model.Payment;
import id.co.travel.travelcore.repository.model.PackageHoliday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
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
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    @Value("${producer.topic.notif}")
    private String topicNotif;


    @Override
    public String submit(Payment payment) throws Exception {
        LOGGER.info("Submit payment");

        LOGGER.info("Get data Customer");
        Customer customer = getDataCustomer(payment.getCustomerId());
        LOGGER.info("Customer " + customer);

        LOGGER.info("Vefify Packages is available");
        Optional<PackageHoliday> optionalPackageHoliday = holidayService.findPackagesById(payment.getPackageId());
        if (optionalPackageHoliday.isEmpty()) {
            throw new CustomException("Packages is not available");
        }
        PackageHoliday packageHoliday = optionalPackageHoliday.get();

        LOGGER.info("Verification Payment");
        dummyVerificationPayment(payment.getCard());

        LOGGER.info("Put Notif Email Kafka");
        Notif notif = new Notif();
        notif.setTitle("Payment Success Package " + packageHoliday.getTitle());
        notif.setBody("Terima kasih\nPayment Success Package " + packageHoliday.getTitle() + "");
        notif.setEmail(customer.getEmail());
        notif.setDescription("Isi Deskripsi email bla bla : " + packageHoliday.getDescription());
        kafkaTemplate.send(topicNotif, objectMapper.writeValueAsString(notif));

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
