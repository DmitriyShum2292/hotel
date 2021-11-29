package com.example.hotels.service;

import com.example.hotels.dto.Payment;
import com.example.hotels.dto.PaymentUrlResponseDTO;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@PropertySource("classpath:external.properties")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @MockBean
    private OrderRepository orderRepository;
    @Autowired
    private HMACUtil hmacUtil;
    @Autowired
    private ExternalApiService externalApiService;

    private static User user;
    private static Order order;

    @Value("citizen.account.baseurl")
    private String BASE_URL;
    @Value("${citizen.account.payment.mapping}")
    private String PAYMENT_URL;
    @Value("${hotels.keyid}")
    private String keyId;
    @Value("${citizen.account.action}")
    private String action;
    @Value("${city.management.legalentity.create.mapping}")
    private String CREATE_NEW_LEGAL_MAPPING;
    @Value("${citizen.account.notification.mapping}")
    private String NOTIFICATION_MAPPING;
    @Value("${citizen.account.redirect.url}")
    private String redirectUrl;

    @BeforeAll
    public static void init(){
        user = new User();
        user.setNickName("Login");
        user.setUserId(67368035);
        order = new Order();
        order.setId(1);
        order.setPeriod(1);
        order.setTotalPrice(2);
        order.setPaid(true);
        user.setOrders(Arrays.asList(order));
    }

    @Test
    void save() {
        //void method
    }

    @Test
    void findById() {
        given(orderRepository.findById(1)).willReturn(order);
        Order orderTest = orderService.findById(1);
        assertThat(orderTest.getId()==1);
    }


    @Test
    void pay() throws IOException {

        Payment payment = new Payment("HOTELS","order",order.getTotalPrice(),
                user.getUserId(),redirectUrl);

        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        String secretKey = "hotelKey";
        String signature = hmacUtil.calculateHash(keyId,timestamp,"action",secretKey);

        String url = "http://54.236.248.165/api/v1/payment/pay-url";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payment);
        StringEntity entity = new StringEntity(json);


        try(CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            httpPost.setEntity(entity);
            httpPost.addHeader("sm-keyid", keyId);
            httpPost.addHeader("sm-timestamp", timestamp);
            httpPost.addHeader("sm-action", "action");
            httpPost.addHeader("sm-signature", signature);
            httpPost.setEntity(entity);
            CloseableHttpResponse response = client.execute(httpPost);

            String obj = EntityUtils.toString(response.getEntity());

            ObjectMapper objectMapper = new ObjectMapper();
            PaymentUrlResponseDTO paymentUrlResponseDTO = objectMapper.readValue(obj, PaymentUrlResponseDTO.class);

            response.close();

            assertThat(paymentUrlResponseDTO!=null);
        }
    }



    @Test
    void delete() {
        //void method
    }
}