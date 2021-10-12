package com.example.hotels;

import com.example.hotels.hmac.HMACUtil;

import java.util.Date;

public class Test {
    public static void main(String[] args) {
        HMACUtil hmacUtil = new HMACUtil();

        String data = String.valueOf(new Date().getTime()+300000);

        String hash = hmacUtil.calculateHash("HOTELS",data,"action","hotelKey");
        System.out.println(hash);
        System.out.println(String.valueOf(data));

        String hash1 = hmacUtil.calculateHash("HOTELS",data,"action","hotelKey");
        System.out.println(hash1);
        System.out.println(String.valueOf(data));
    }
}
