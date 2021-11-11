package com.example.hotels.repository;

import com.example.hotels.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private  UserRepository userRepository;
    private static final String nickName = "Dmitriy";
    private static long id;

    @BeforeEach
    public void init(){
        User user = new User();
        user.setNickName(nickName);
        user.setPassword("Password!");
        user.setUserId(1);
        userRepository.save(user);
        id = user.getId();
    }
    @AfterEach
    public void after(){
        userRepository.delete(userRepository.findById(id).get());
    }

    @Test
    public void saveAndFindByLoginUserTest(){
        assertEquals(userRepository.findByNickName(nickName).getNickName(),nickName);
    }
    @Test
    public void findByUserId(){
        assertEquals(userRepository.findById(id).get().getId(),id);
    }
}
