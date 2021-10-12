package com.example.hotels.service.impl;

import com.example.hotels.dto.DistrictDTO;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.User;
import com.example.hotels.repository.HotelRepository;
import com.example.hotels.service.ExternalApiService;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.UserService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@PropertySource("classpath:external.properties")
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private HMACUtil hmacUtil;
    @Autowired
    private ExternalApiService externalApiService;

    @Value("${city.management.baseurl}")
    private String BASE_URL;
    @Value("${city.management.districts.mapping}")
    private String DISTRICTS_MAPPING;

    public Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);


    @Override
    public void save(Hotel hotel) {
        hotelRepository.save(hotel);
    }

    @Override
    public Hotel findById(long id) {
        return hotelRepository.findById(id);
    }

    @Override
    public Hotel findByName(String name) {
        return hotelRepository.findByName(name);
    }

    @Override
    public List<Hotel> findAllByNameStartingWithIgnoreCase(String name) {
        return hotelRepository.findAllByNameStartingWithIgnoreCase(name);
    }

    @Override
    public Page<Hotel> findAll(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sort));
        return hotelRepository.findAll(pageable);
    }


    @Override
    public void delete(long id) {
        User user = userService.findCurrentUser();
        Hotel hotel = findById(id);
        user.setHotel(null);
        userService.save(user);
        hotelRepository.delete(hotel);
    }

    @Override
    public List<DistrictDTO> getAllDistricts() throws IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + DISTRICTS_MAPPING).newBuilder();
        String url = urlBuilder.build().toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONObject obj = new JSONObject(EntityUtils.toString(entity));

        Gson gson = new Gson();
        List<DistrictDTO> districtDTOS = new ArrayList<>();
        districtDTOS = gson.fromJson(obj.getJSONArray("result").toString(),
                new TypeToken<List<DistrictDTO>>(){}.getType());
        System.out.println(districtDTOS);
        if (response.getStatusLine().getStatusCode()==200) {

            return districtDTOS;
        }
        response.close();
        httpClient.close();
        return new ArrayList<>();
    }
}
