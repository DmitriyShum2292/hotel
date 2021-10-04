package com.example.hotels.service.impl;

import com.example.hotels.dto.Notification;
import com.example.hotels.dto.Payment;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.OrderRepository;
import com.example.hotels.service.OrderService;
import com.example.hotels.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;
    @Autowired
    private OrderRepository orderRepository;
    private OkHttpClient okHttpClient = new OkHttpClient();
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

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
        Payment payment = new Payment(order.getTotalPrice(), new Date(),
                user.getUserId(), order.isPaid(), order.toString());
        String url = "http://localhost:8090/test";
        String json = null;
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        try {
            json = ow.writeValueAsString(payment);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = okHttpClient.newCall(request).execute();

        Payment responsePayment = new Gson().fromJson(response.body().string(),Payment.class);
        if (response.code()==200 && responsePayment.isPaid()==true){
            order.setPaid(true);
            orderRepository.save(order);
            sendNotification(order.getHotel());
            return true;
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
