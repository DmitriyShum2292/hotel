package com.example.hotels.service;

import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    public void init(){
        user = new User();
        user.setId(1);
        user.setUserId(1);
        user.setLogin("Login");
        user.setOrders(Arrays.asList(new Order()));
    }

    @Test
    void save() {
        //void method
    }

    @Test
    void findByLogin() {
        when(userRepository.findByLogin(user.getLogin())).thenReturn(user);
        User testUser = userService.findByLogin(user.getLogin());
        assertThat(testUser.getLogin().equals(user.getLogin()));
    }

    @Test
    void findCurrentUser() {
        // don't know hot testing this method
    }

    @Test
    void findOrdersByUser() {
        assertThat(user.getOrders().size()>0);
    }

    @Test
    void findByUserId() {
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        User testUser = userService.findByUserId(user.getUserId());
        assertThat(testUser.getUserId()==user.getUserId());
    }
}