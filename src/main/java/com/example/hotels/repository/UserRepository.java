package com.example.hotels.repository;

import com.example.hotels.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByNickName(String nickName);
    User findByUserId(long id);
}
