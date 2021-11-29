package com.example.hotels.service;

import com.example.hotels.dto.ResidentReponseDTO;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.Order;
import com.example.hotels.model.User;
import com.example.hotels.repository.UserRepository;
import com.google.gson.Gson;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.PropertySource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
@PropertySource("classpath:external.properties")
class UserServiceTest {

    @Autowired
    private UserService userService;
    @MockBean
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

    private static User user;

    @BeforeAll
    public static void init(){
        user = new User();
        user.setId(1);
        user.setUserId(67368035);
        user.setNickName("Login");
        user.setOrders(Arrays.asList(new Order()));
    }

    /**
     * This http method has been commented because in external module periodically changes citizen card number
     */
    @Test
    void citizenExist() throws IOException, JSONException {
        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);

        String signature = hmacUtil.calculateHash(keyId,timestamp,action,"hotelKey");

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

            JSONObject obj = new JSONObject(EntityUtils.toString(entity));
            Gson gson = new Gson();
            ResidentReponseDTO residentReponseDTO = gson.fromJson(obj.getJSONObject("result").toString(),
                    ResidentReponseDTO.class);
            boolean result = response.getStatusLine().getStatusCode()==200;
            boolean result2 = residentReponseDTO.isActive();
            assertThat(result).isTrue();
            assertThat(result2).isTrue();
        }
    }

    @Test
    void findByLogin() {
        when(userRepository.findByNickName(user.getNickName())).thenReturn(user);
        User testUser = userService.findByNickName(user.getNickName());
        boolean result = testUser.getNickName().equals(user.getNickName());
        assertThat(result).isTrue();
    }

    @Test
    void findOrdersByUser() {
        boolean result = user.getOrders().size()>0;
        assertThat(result).isTrue();
    }

    @Test
    void findByUserId() {
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        User testUser = userService.findByUserId(user.getUserId());
        boolean result = testUser.getUserId()==user.getUserId();
        assertThat(result).isTrue();
    }
}