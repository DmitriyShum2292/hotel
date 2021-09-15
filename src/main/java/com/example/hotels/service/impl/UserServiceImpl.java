package com.example.hotels.service.impl;

import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.UserRepository;
import com.example.hotels.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user){
        userRepository.save(user);
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User findCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = findByLogin(userDetails.getUsername());
        return user;
    }

    @Override
    public List<Order> findOrdersByUser() {
        User user = findCurrentUser();
        return user.getOrders();
    }
    public void update(User user){
        userRepository.save(user);
    }
    @Override
    public User findByUserId(long id){
        return userRepository.findByUserId(id);
    }
}
