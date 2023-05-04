package id.co.travel.travelcore.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ApplicationProperties {
    @Value("${url.get.data.customer}")
    private String urlGetDataCustomer;
    @Value("${url.crypto.payment}")
    private String urlCryptoPayment;

    @Value("${dummy.service.response}")
    private String dummyServiceResponse;
}
