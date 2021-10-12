package com.example.hotels.service;

import com.example.hotels.model.ExternalApiCredentials;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public interface ExternalApiService {
    ExternalApiCredentials findByKeyId(String keyId);
}
