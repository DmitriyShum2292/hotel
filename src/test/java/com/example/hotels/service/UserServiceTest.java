package com.example.hotels.service;

import com.example.hotels.dto.ResidentReponseDTO;
import com.example.hotels.hmac.HMACUtil;
import com.example.hotels.model.ExternalApiCredentials;
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
    private String BASE_URL;
    @Value("${city.management.resident.card.mapping}")
    private String RESIDENT_CARD_URL;
    @Value("${hotels.keyid}")
    private String keyId;
    @Value("${city.management.action.get}")
    private String action;

    private static User user;

    @BeforeAll
    public static void init(){
        user = new User();
        user.setId(1);
        user.setUserId(14876580);
        user.setNickName("Login");
        user.setOrders(Arrays.asList(new Order()));
    }

    @Test
    void save() {
        //void method
    }

    @Test
    void citizenExist() throws IOException, JSONException {
        long now = new Date().getTime()+30000000;
        String timestamp = String.valueOf(now);
        ExternalApiCredentials externalApiCredentials = externalApiService.findByKeyId(keyId);
        String signature = hmacUtil.calculateHash(keyId,timestamp,action,externalApiCredentials.getValue());

        HttpUrl.Builder urlBuilder
                = HttpUrl.parse(BASE_URL + RESIDENT_CARD_URL+user.getUserId()).newBuilder();
        String url = urlBuilder.build().toString();

        CloseableHttpClient httpClient = HttpClients.createDefault();

        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");
        request.addHeader("sm-keyid",keyId);
        request.addHeader("sm-timestamp",timestamp);
        request.addHeader("sm-action",action);
        request.addHeader("sm-signature",signature);
        CloseableHttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();

        JSONObject obj = new JSONObject(EntityUtils.toString(entity));
        Gson gson = new Gson();
        ResidentReponseDTO residentReponseDTO = gson.fromJson(obj.getJSONObject("result").toString(),ResidentReponseDTO.class);

        assertThat(response.getStatusLine().getStatusCode()==200);
        assertThat(residentReponseDTO.isActive());
    }

    @Test
    void findByLogin() {
        when(userRepository.findByNickName(user.getNickName())).thenReturn(user);
        User testUser = userService.findByNickName(user.getNickName());
        assertThat(testUser.getNickName().equals(user.getNickName()));
    }

    @Test
    void findOrdersByUser() {
        assertThat(user.getOrders().size()>0);
    }

    @Test
    void findByUserId() {
        given(userRepository.findByUserId(user.getUserId())).willReturn(user);
        User testUser = userService.findByUserId(user.getUserId());
        assertThat(testUser.getUserId()==user.getUserId());
    }
}