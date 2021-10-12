package com.example.hotels.service;

import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public interface UserService {

    void save(User user);
    User findByLogin(String login);
    boolean citizenExist(User user) throws IOException;
    User findCurrentUser();
    List<Order> findOrdersByUser();
    User findByUserId(long id);
}
