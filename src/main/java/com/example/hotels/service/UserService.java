package com.example.hotels.service;

import com.example.hotels.dto.ResidentReponseDTO;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

@Component
public interface UserService {

    void save(User user);
    User findById(long id);
    User findByNickName(String nickName);
    boolean citizenExist(User user) throws IOException;
    ResidentReponseDTO getCitizenInfo(User user) throws IOException;
    User findCurrentUser();
    List<Order> findOrdersByUser();
    User findByUserId(long id);
}
