package id.co.travel.travelcore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.co.travel.travelcore.config.ApplicationProperties;
import id.co.travel.travelcore.constants.Constants;
import id.co.travel.travelcore.exception.CustomException;
import id.co.travel.travelcore.model.*;
import id.co.travel.travelcore.repository.model.PackageHoliday;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
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

        if(packageHoliday.getQuotaLeft() <= 0) {
            throw new CustomException("Quota packages is not available");
        }

        LOGGER.info("Verification Payment");
        dummyVerificationPayment(payment);

        packageHoliday.setQuotaLeft(packageHoliday.getQuotaLeft() - 1);
        holidayService.updatePackagesById(packageHoliday);

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

    private void dummyVerificationPayment(Payment payment) throws CustomException {
        if(payment.getPaymentType() == Constants.PAYMENT_TYPE.CARD_TYPE) {
            //Dummy Call Service Verification Card Payment;
            if (!applicationProperties.getDummyServiceResponse().equals("OK")) {
                throw new CustomException("Payment Failed");
            }
            LOGGER.info("Verification Payment Card Success");
        } else if(payment.getPaymentType() == Constants.PAYMENT_TYPE.CRYPTO_TYPE){
            try {
                ResponseEntity<CryptoPaymentResponse> response
                        = restTemplate.exchange(applicationProperties.getUrlCryptoPayment(), HttpMethod.PUT, new HttpEntity<>(payment.getCrypto(), createHeaders(payment.getCryptoCredential())), CryptoPaymentResponse.class);
                LOGGER.info("Response Payment Crypto : "+objectMapper.writeValueAsString(response));
                if(response.getBody() != null && response.getBody().isStatus()){
                    LOGGER.info("Verification Payment Crypto Success");
                }
                else {
                    LOGGER.info("Payment Failed : "+response.getBody().getMessage());
                    throw new CustomException("Payment Failed");
                }
            } catch (HttpClientErrorException e) {
                LOGGER.info("HttpClientErrorException : "+e);
                throw new CustomException("Unauthorized : "+e.getMessage());
            } catch (Exception e) {
                throw new CustomException(e.getMessage());
            }
        }
    }
    private HttpHeaders createHeaders(CryptoCredential cryptoCredential){
        return new HttpHeaders() {{
            String auth = cryptoCredential.getUserId() + ":" + cryptoCredential.getPassword();
            byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(), false);
            String authHeader = "Basic " + new String( encodedAuth );
            set( "Authorization", authHeader );
        }};
    }
}
