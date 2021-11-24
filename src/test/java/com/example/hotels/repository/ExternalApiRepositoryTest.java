package com.example.hotels.repository;

import com.example.hotels.model.ExternalApiCredentials;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExternalApiRepositoryTest {

    @Autowired
    private ExternalApiRepository externalApiRepository;
    private ExternalApiCredentials externalApiCredentials = new ExternalApiCredentials();

    @Test
    void findByKeyId() {
        externalApiCredentials.setKeyId("test");
        externalApiRepository.save(externalApiCredentials);
        assertEquals(externalApiRepository.findByKeyId("test").getKeyId(),externalApiCredentials.getKeyId());
    }
}