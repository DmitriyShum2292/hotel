package com.example.hotels.service.impl;

import com.example.hotels.dto.*;
import com.example.hotels.exception.NotFoundException;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Hotel;
import com.example.hotels.model.User;
import com.example.hotels.repository.HotelRepository;
import com.example.hotels.service.ExternalApiService;
import com.example.hotels.service.HotelService;
import com.example.hotels.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.HttpUrl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
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
import java.util.Date;
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

    @Value("${hotels.keyid}")
    private String keyId;
    @Value("${city.management.baseurl}")
    private String baseUrl;
    @Value("${city.management.districts.mapping}")
    private String districtsMapping;
    @Value("${city.management.streets.mapping}")
    private String streetMapping;
    @Value("${city.management.commercial.mapping}")
    private String commercialMapping;
    @Value("${city.management.homeid.mapping}")
    private String homeIdMapping;
    @Value("${city.management.legalentity.create.mapping}")
    private String legalEntityCreateMapping;
    private static final String USER_AGENT = "Googlebot";
    private static final String RESULT = "result";

    public static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Override
    public void save(Hotel hotel){
     hotelRepository.save(hotel);
    }

    @Override
    public void saveWithVerification(Hotel hotel) throws IOException {
        if(saveLegalEntityInCityManagement(hotel.getHomeId(),hotel)) {
            hotelRepository.save(hotel);
        }
    }

    @Override
    public void update(Hotel hotel) {
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
    /**
     * http request to city management for get all districts
     */
    @Override
    public List<DistrictDTO> getAllDistricts() throws IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + districtsMapping).newBuilder();
        String url = urlBuilder.build().toString();
        CloseableHttpResponse response;
        HttpEntity entity;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
            response = httpClient.execute(request);
            entity = response.getEntity();

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));

            Gson gson = new Gson();
            List<DistrictDTO> districtDTOS;
            districtDTOS = gson.fromJson(obj.getJSONArray(RESULT).toString(),
                    new TypeToken<List<DistrictDTO>>() {
                    }.getType());
            if (response.getStatusLine().getStatusCode() == 200) {
                return districtDTOS;
            }
            return new ArrayList<>();
        }
    }
    /**
     * http request to city management for get streets by district
     */
    @Override
    public List<StreetDTO> getAllStreetsByDistrict(long districtId) throws IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + streetMapping +"?districtId="+districtId).newBuilder();
        String url = urlBuilder.build().toString();
        CloseableHttpResponse response;
        HttpEntity entity;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
            response = httpClient.execute(request);
            entity = response.getEntity();

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));

            Gson gson = new Gson();
            List<StreetDTO> streets = gson.fromJson(obj.getJSONArray(RESULT).toString(),
                    new TypeToken<List<StreetDTO>>() {
                    }.getType());
            if (response.getStatusLine().getStatusCode() == 200) {
                return streets;
            }
            return new ArrayList<>();
        }
    }
    /**
     * http request to city management for get buildings by street
     */
    @Override
    public List<CommercialBuildingDTO> getAllCommercialBuildingByStreet(long streetId) throws IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + commercialMapping +"?streetId="+streetId).newBuilder();
        String url = urlBuilder.build().toString();
        CloseableHttpResponse response;
        HttpEntity entity;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
            response = httpClient.execute(request);
            entity = response.getEntity();

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));

            Gson gson = new Gson();
            List<CommercialBuildingDTO> buildings = gson.fromJson(obj.getJSONArray(RESULT).toString(),
                    new TypeToken<List<CommercialBuildingDTO>>() {
                    }.getType());
            if (response.getStatusLine().getStatusCode() == 200) {
                return buildings;
            }
            return new ArrayList<>();
        }
    }
    /**
     * http request to city management for get offices and info by commercial building
     */
    @Override
    public List<BuildingInfoDTO> getAllBuildingInfoByCommercialBuilding(long commercialBuildingId) throws IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + homeIdMapping +"?buildingId="+commercialBuildingId).newBuilder();
        String url = urlBuilder.build().toString();
        HttpEntity entity;
        CloseableHttpResponse response;
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
            response = httpClient.execute(request);
            entity = response.getEntity();

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));

            List<BuildingInfoDTO> buildingInfoDTOS;
            Gson gson = new Gson();
            try {
                buildingInfoDTOS = gson.fromJson(obj.getJSONArray(RESULT).toString(),
                        new TypeToken<List<BuildingInfoDTO>>() {
                        }.getType());
            } catch (JSONException exception) {
                throw new NotFoundException("Cannot create hotel in this building");
            }
            if (response.getStatusLine().getStatusCode() == 200) {
                return buildingInfoDTOS;
            }
            return new ArrayList<>();
        }
    }
    /**
     * http post request with HMAC headers to city management for save new legal entity and set owner
     */
    @Override
    public boolean saveLegalEntityInCityManagement(long homeId,Hotel hotel) throws IOException {
        User user = userService.findCurrentUser();
        NewLegalEntityDTO newLegalEntityDTO = new NewLegalEntityDTO(hotel.getName(),homeId, user.getUserId());

        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        String secretKey = externalApiService.findByKeyId(keyId).getValue();
        String signature = hmacUtil.calculateHash(keyId,timestamp,"action",secretKey);

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + legalEntityCreateMapping).newBuilder();
        String url = urlBuilder.build().toString();
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(newLegalEntityDTO);
        StringEntity entity = new StringEntity(json);

        try(CloseableHttpClient client = HttpClients.createDefault()) {

            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Content-type", "application/json");
            httpPost.addHeader(HttpHeaders.USER_AGENT, USER_AGENT);
            httpPost.setEntity(entity);
            httpPost.addHeader("sm-keyid", keyId);
            httpPost.addHeader("sm-timestamp", timestamp);
            httpPost.addHeader("sm-action", "action");
            httpPost.addHeader("sm-signature", signature);
            httpPost.setEntity(entity);

            CloseableHttpResponse response = client.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 201) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}
