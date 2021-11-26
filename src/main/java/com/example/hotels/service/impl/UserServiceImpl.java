package com.example.hotels.service.impl;


import com.example.hotels.dto.ResidentReponseDTO;
import com.example.hotels.exception.NotFoundException;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.ExternalApiCredentials;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.UserRepository;
import com.example.hotels.service.ExternalApiService;
import com.example.hotels.service.UserService;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.hibernate.LazyInitializationException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.io.IOException;

import java.util.*;

@Service
@PropertySource("classpath:external.properties")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private HMACUtil hmacUtil;
    @Autowired
    private ExternalApiService externalApiService;

    @Value("${city.management.baseurl}")
    private String baseUrl;
    @Value("${city.management.resident.card.mapping}")
    private String residentCardUrl;
    @Value("${hotels.keyid}")
    private String keyId;
    @Value("${city.management.action.get}")
    private String action;

    @Override
    public void save(User user){
        try {
            if (citizenExist(user)){
                userRepository.save(user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User findById(long id) {
        return userRepository.findById(id);
    }

    /**
     * http get request to city management for check exist user
     */
    @Override
    public boolean citizenExist(User user) throws IOException {
        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        ExternalApiCredentials externalApiCredentials = externalApiService.findByKeyId(keyId);
        String signature = hmacUtil.calculateHash(keyId,timestamp,action,externalApiCredentials.getValue());

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + residentCardUrl +user.getUserId()).newBuilder();
        String url = urlBuilder.build().toString();

        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            request.addHeader("sm-keyid", keyId);
            request.addHeader("sm-timestamp", timestamp);
            request.addHeader("sm-action", action);
            request.addHeader("sm-signature", signature);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new NotFoundException
                        ("This user is not active or does not exist and does not have the right to create a hotel");
            }

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            Gson gson = new Gson();
            ResidentReponseDTO residentReponseDTO = gson.fromJson(obj.getJSONObject("result").toString(), ResidentReponseDTO.class);

            if (response.getStatusLine().getStatusCode() == 200 && residentReponseDTO.isActive()) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    @Override
    public ResidentReponseDTO getCitizenInfo(User user) throws IOException {
        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        ExternalApiCredentials externalApiCredentials = externalApiService.findByKeyId(keyId);
        String signature = hmacUtil.calculateHash(keyId,timestamp,action,externalApiCredentials.getValue());

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + residentCardUrl +user.getUserId()).newBuilder();
        String url = urlBuilder.build().toString();

        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
            request.addHeader("sm-keyid", keyId);
            request.addHeader("sm-timestamp", timestamp);
            request.addHeader("sm-action", action);
            request.addHeader("sm-signature", signature);
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new NotFoundException
                        ("This user is not active or does not exist and does not have the right to create a hotel");
            }

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            Gson gson = new Gson();
            ResidentReponseDTO residentReponseDTO = gson.fromJson(obj.getJSONObject("result").toString(), ResidentReponseDTO.class);

            if (response.getStatusLine().getStatusCode() == 200 && residentReponseDTO.isActive()) {
                return residentReponseDTO;
            } else {
                return new ResidentReponseDTO();
            }
        }
    }

    @Override
    public User findByNickName(String nickName) {
        return userRepository.findByNickName(nickName);
    }

    @Override
    public User findCurrentUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
                getAuthentication().getPrincipal();
        User user = findByNickName(userDetails.getUsername());
        return user;
    }

    @Override
    @Transactional(rollbackOn = LazyInitializationException.class)
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
