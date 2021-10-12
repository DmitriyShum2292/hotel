package com.example.hotels.service.impl;

import com.example.hotels.model.ExternalApiCredentials;
import com.example.hotels.repository.ExternalApiRepository;
import com.example.hotels.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    @Autowired
    private ExternalApiRepository externalApiRepository;

    @Override
    public ExternalApiCredentials findByKeyId(String keyId) {
        return externalApiRepository.findByKeyId(keyId);
    }
}
