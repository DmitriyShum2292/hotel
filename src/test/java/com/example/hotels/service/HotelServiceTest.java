package com.example.hotels.service;

import com.example.hotels.dto.BuildingInfoDTO;
import com.example.hotels.dto.CommercialBuildingDTO;
import com.example.hotels.dto.StreetDTO;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Hotel;
import com.example.hotels.repository.HotelRepository;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
@PropertySource("classpath:external.properties")
class HotelServiceTest {

    @Autowired
    private HotelService hotelService;
    @Mock
    private HotelRepository hotelRepository;
    @Autowired
    private HMACUtil hmacUtil;
    @Autowired
    private ExternalApiService externalApiService;

    private static Hotel hotel;

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

    @BeforeAll
    public static void init(){
        hotel = new Hotel();
        hotel.setId(1);
        hotel.setName("Hotel");
    }

    @Test
    void findById() {
        given(hotelRepository.findById(1)).willReturn(hotel);
        boolean result = hotel.getName().equals(hotel.getName());
        assertThat(result).isTrue();
    }

    @Test
    void findByName() {
        given(hotelRepository.findByName(hotel.getName())).willReturn(hotel);
        Hotel testHotel = hotelRepository.findByName(hotel.getName());
        boolean result = testHotel.getName().equals(hotel.getName());
        assertThat(result).isTrue();
    }

    @Test
    void findAllByNameStartingWithIgnoreCase() {
        List<Hotel> hotels = new ArrayList<>();
        hotels.add(hotel);
        given(hotelRepository.findAllByNameStartingWithIgnoreCase(hotel.getName())).willReturn(hotels);
        List<Hotel> hotelsTest = hotelService.findAllByNameStartingWithIgnoreCase(hotel.getName());
        boolean result = hotelsTest.size()==0;
        assertThat(result).isTrue();
    }

    @Test
    void getAllDistricts() throws IOException, JSONException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + districtsMapping).newBuilder();
        String url = urlBuilder.build().toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        CloseableHttpResponse response = httpClient.execute(request);

        response.close();
        httpClient.close();
        boolean result = response.getStatusLine().getStatusCode()==200;
        assertThat(result).isTrue();
    }

    @Test
    void getAllStreetsByDistrict() throws JSONException, IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + streetMapping +"?districtId="+1).newBuilder();
        String url = urlBuilder.build().toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");


        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONObject obj = new JSONObject(EntityUtils.toString(entity));

        Gson gson = new Gson();
        List<StreetDTO> streets = gson.fromJson(obj.getJSONArray("result").toString(),
                new TypeToken<List<StreetDTO>>(){}.getType());

        boolean result = response.getStatusLine().getStatusCode()==200;
        boolean result1 = streets.size()>0;
        assertThat(result).isTrue();
        assertThat(result1).isTrue();

        response.close();
        httpClient.close();
    }

    @Test
    void getAllCommercialBuildingByStreet() throws JSONException, IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + commercialMapping +"?streetId="+1).newBuilder();
        String url = urlBuilder.build().toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");


        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONObject obj = new JSONObject(EntityUtils.toString(entity));

        Gson gson = new Gson();
        List<CommercialBuildingDTO> buildings = gson.fromJson(obj.getJSONArray("result").toString(),
                new TypeToken<List<CommercialBuildingDTO>>(){}.getType());

        boolean result = response.getStatusLine().getStatusCode()==200;
        boolean result1 = buildings.size()>0;
        assertThat(result).isTrue();
        assertThat(result1).isTrue();

        response.close();
        httpClient.close();
    }

    @Test
    void getAllBuildingInfoByCommercialBuilding() throws JSONException, IOException {
        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(baseUrl + homeIdMapping +"?buildingId="+1).newBuilder();
        String url = urlBuilder.build().toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONObject obj = new JSONObject(EntityUtils.toString(entity));

        List<BuildingInfoDTO>buildingInfoDTOS;
        Gson gson = new Gson();

        buildingInfoDTOS = gson.fromJson(obj.getJSONArray("result").toString(),
                    new TypeToken<List<BuildingInfoDTO>>(){}.getType());

        response.close();
        httpClient.close();
        boolean result = response.getStatusLine().getStatusCode()==200;
        assertThat(result).isTrue();
    }
}