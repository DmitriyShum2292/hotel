package com.example.hotels.service;

import com.example.hotels.model.ExternalApiCredentials;
import com.example.hotels.repository.ExternalApiRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class ExternalApiServiceTest {

    @Autowired
    private ExternalApiService externalApiService;
    @Mock
    ExternalApiRepository externalApiRepository;
    private static final String test = "test";

    @Test
    void findByKeyId() {
        given(externalApiRepository.findByKeyId("test"))
                .willReturn(new ExternalApiCredentials(1,test,test));
        boolean result = externalApiRepository.findByKeyId(test).getValue().equals(test);
        assertThat(result).isTrue();
    }
}