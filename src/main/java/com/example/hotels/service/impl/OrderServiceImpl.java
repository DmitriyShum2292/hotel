package com.example.hotels.service.impl;

import com.example.hotels.dto.Notification;
import com.example.hotels.dto.Payment;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.OrderRepository;
import com.example.hotels.service.ExternalApiService;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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

    @Override
    public void save(Order order) {
        User user = userService.findCurrentUser();
        user.getOrders().add(order);
        orderRepository.save(order);
        userService.save(user);
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id);
    }


    @Override
    public boolean pay(Order order) throws IOException {
        User user = userService.findCurrentUser();
        Payment payment = new Payment(order.getTotalPrice(),user.getUserId(),order.toString(),keyId);

        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        String secretKey = externalApiService.findByKeyId(keyId).getValue();
        String signature = hmacUtil.calculateHash(keyId,timestamp,action,secretKey);

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + PAYMENT_URL+user.getUserId()).newBuilder();
        String url = urlBuilder.build().toString();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(payment);
        StringEntity entity = new StringEntity(json);

        CloseableHttpClient client = HttpClients.createDefault();
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(entity);
            httpPost.addHeader("sm-keyid",keyId);
            httpPost.addHeader("sm-timestamp",timestamp);
            httpPost.addHeader("sm-action",action);
            httpPost.addHeader("sm-signture",signature);

            CloseableHttpResponse response = client.execute(httpPost);
            if(response.getStatusLine().getStatusCode()==200){
                order.setPaid(true);
                orderRepository.save(order);
                return true;
            }
        }
        finally {
            client.close();
        }
        return false;
    }

    @Override
    public boolean sendNotification(Hotel hotel) throws IOException {
        String url = "http://localhost:8090/test";
        String json = null;
        Notification notification = new Notification(hotel.getCleaningTime(),hotel.toString());
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            json = ow.writeValueAsString(notification);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();

        Notification responseNotification = new Gson().fromJson(response.body().string(),Notification.class);

        if (response.code()==200&&!responseNotification.getDescription().equals("Error")){
            return true;
        }
        return false;
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
}
