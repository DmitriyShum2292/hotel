package com.example.hotels.repository;

import com.example.hotels.model.ExternalApiCredentials;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExternalApiRepository extends JpaRepository<ExternalApiCredentials,Long> {

    ExternalApiCredentials findByKeyId(String keyId);
}
