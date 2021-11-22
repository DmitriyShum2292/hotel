package com.example.hotels.repository;

import com.example.hotels.model.User;
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


    @Test
    public void findByUserId(){
        User user = new User();
        user.setNickName(nickName);
        user.setPassword("Password!");
        user.setUserId(1);
        userRepository.save(user);
        id= user.getId();
        assertEquals(userRepository.findById(id).get().getId(),id);
        userRepository.delete(user);
    }

    @Test
    void findByNickName() {
        User user = new User();
        user.setNickName("NeDmitriy");
        user.setPassword("password");
        userRepository.save(user);
        assertEquals(userRepository.findByNickName("NeDmitriy").getNickName(),"NeDmitriy");
        userRepository.delete(user);
    }
}
