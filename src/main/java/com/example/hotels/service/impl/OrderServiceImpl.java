package com.example.hotels.service.impl;

import com.example.hotels.dto.*;
import com.example.hotels.exception.NotFoundException;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.OrderRepository;
import com.example.hotels.service.ExternalApiService;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import okhttp3.*;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@PropertySource("classpath:external.properties")
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private HMACUtil hmacUtil;
    @Autowired
    private ExternalApiService externalApiService;

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Value("citizen.account.baseurl")
    private String baseUrl;
    @Value("${citizen.account.payment.mapping}")
    private String paymentUrl;
    @Value("${hotels.keyid}")
    private String keyId;
    @Value("${citizen.account.action}")
    private String action;
    @Value("${city.management.legalentity.create.mapping}")
    private String createNewLegalMapping;
    @Value("${citizen.account.notification.mapping}")
    private String notificationMapping;
    @Value("${citizen.account.redirect.url}")
    private String redirectUrl;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public void save(Order order) {
        User user = userService.findCurrentUser();
        user.getOrders().add(order);
        order.setUser(user);
        orderRepository.save(order);
        userService.save(user);
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id);
    }

    @Override
    public List<Order> findAllByHotelAndUser(Hotel hotel, User user) {
        return orderRepository.findAllByHotelAndUser(hotel,user);
    }


    @Override
    public List<Order> findByHotel(Hotel hotel) {
        return orderRepository.findByHotel(hotel);
    }

    /**
     * http method for get payment url from citizen account
     */
    @Override
    public String pay(Order order) throws IOException {
        User user = userService.findCurrentUser();
        Payment payment = new Payment("HOTELS","order",order.getTotalPrice(),
                user.getUserId(),redirectUrl);

        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        String secretKey = externalApiService.findByKeyId(keyId).getValue();
        String signature = hmacUtil.calculateHash(keyId,timestamp,"action",secretKey);


        String url = "http://54.236.248.165/api/v1/payment/pay-url";
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payment);
        StringEntity entity = new StringEntity(json);

        logger.info(url);
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
            logger.info(obj);
            response.close();
            if (paymentUrlResponseDTO != null) {
                return paymentUrlResponseDTO.getObject();
            }
            throw new NotFoundException("User with citizen card number: " + user.getUserId() + " doesn't exist");
        }
    }

    @Override
    public boolean sendNotification(Hotel hotel) throws IOException {
        User user = userService.findCurrentUser();
        NotificationDTO notificationDTO = new NotificationDTO(user.getUserId(),
                hotel.getCleaningTime(),hotel.toString());

        String url = notificationMapping;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(notificationDTO);
        StringEntity entity = new StringEntity(json);

        try(CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            httpPost.setEntity(entity);

            CloseableHttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 200) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public void delete(long id) {
        User user = userService.findCurrentUser();
        List<Order> orders = user.getOrders();
        List<Order>toDelete = new ArrayList<>();
        for (Order order : orders){
            if (order.getId() == id){
                toDelete.add(order);
            }
        }
        orders.removeAll(toDelete);
        user.setOrders(orders);
        userService.save(user);
        orderRepository.deleteById(id);
    }

    @Override
    public void setPaid(CompleteRequestDTO completeRequestDTO) throws IOException {
        if (completeRequestDTO.getSuccess()){
        User user = userService.findByUserId(completeRequestDTO.getCitizenCardId());
        Optional<Order> filteredOrder = user.getOrders().stream()
                .filter(c -> !c.isPaid())
                .findFirst();
        if (filteredOrder.isPresent()){
            Order order = filteredOrder.get();
            order.setPaid(true);
            orderRepository.save(order);
        }
        }
    }
}
