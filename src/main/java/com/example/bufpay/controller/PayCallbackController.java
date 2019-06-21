package com.example.bufpay.controller;

import com.example.bufpay.utils.HttpsClientRequestFactory;
import com.example.bufpay.utils.MD5Utils;
import com.example.bufpay.utils.SslUtils;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.util.DigestUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author sonny
 * @Description
 * @Date 2019/6/19 15:04
 **/
@RestController
public class PayCallbackController {

//    @RequestMapping(value = "/payCallback", method = RequestMethod.POST)
//    public String payCallback() {
//        try {
//            String result = doHttpsPostTest();
//            return result;
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }

}
