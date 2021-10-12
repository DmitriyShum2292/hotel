package com.example.hotels.hmac;

import org.springframework.stereotype.Component;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class HMACUtil {

    public String calculateHash(String keyId, String timestamp, String action,String secretKey){
        String data = "keyId="+keyId+";timestamp="+timestamp+";action="+action;

        SecretKeySpec signInKey = new SecretKeySpec(secretKey.getBytes(),"HmacSHA256");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(signInKey);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        byte[] rawHmac = mac.doFinal(data.getBytes());
        return new String(Base64.getEncoder().encode(rawHmac));
    }
}
