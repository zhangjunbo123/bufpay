package com.example.bufpay.controller;

import com.example.bufpay.utils.HttpsClientRequestFactory;
import com.example.bufpay.utils.SslUtils;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
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
public class PayController {

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String pay() {
        try {
            String result = doHttpsPostTest();
            return result;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/pay2", method = RequestMethod.GET)
    public void pay2() {
        RestTemplate client = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        // 以表单的方式提交
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //将请求头部和参数合成一个请求
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String url = "https://bufpay.com/api/pay/97102";
        params.add("name", "product1");
        params.add("pay_type", "alipay");
        params.add("price", "0");
        params.add("order_uid", "user001");
        params.add("notify_url", "http://www.baidu.com");
        params.add("return_url", "http://www.baidu.com");
        params.add("feedback_url", "");
        params.add("secret", "a0e8f0bc942c44c3ac1f1d438830fa33");
        params.add("order_id", "121");
        params.add("sign", "1E35ABFE58C523219AE0831CD70F19FC");
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
        //执行HTTP请求，将返回的结构使用ResultVO类格式化
        try {
            SslUtils.ignoreSsl();
        } catch (Exception e) {
            e.printStackTrace();
        }
        ResponseEntity response = client.exchange(url, method, requestEntity, String.class);
        System.out.println("。。。完毕");
    }

    @RequestMapping(value = "/s", method = RequestMethod.GET)
    public String s() {
        return "s";
    }

    public String postRequest(String urlAddress,String args,int timeOut) throws Exception{
        URL url = new URL(urlAddress);
        if("https".equalsIgnoreCase(url.getProtocol())){
            SslUtils.ignoreSsl();
        }
        URLConnection u = url.openConnection();
        u.setDoInput(true);
        u.setDoOutput(true);
        u.setConnectTimeout(timeOut);
        u.setReadTimeout(timeOut);
        OutputStreamWriter osw = new OutputStreamWriter(u.getOutputStream(), "UTF-8");
        osw.write(args);
        osw.flush();
        osw.close();
        u.getOutputStream();
        return IOUtils.toString(u.getInputStream());
    }

    /**
     * RestTemplate 发送  HTTP POST请求 --- 测试
     * @throws UnsupportedEncodingException
     *
     * @date 2018年9月8日 下午2:12:50
     */
    public void doHttpPostTest() throws UnsupportedEncodingException {
        // -------------------------------> 获取Rest客户端实例
        RestTemplate restTemplate = new RestTemplate();

        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1); // 移除原来的转换器
        // 设置字符编码为utf-8
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
        restTemplate.setMessageConverters(converterList);

        // -------------------------------> (选择性设置)请求头信息
        // HttpHeaders实现了MultiValueMap接口
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置contentType
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        // 给请求header中添加一些数据
        httpHeaders.add("JustryDeng", "这是一个大帅哥!");

        // ------------------------------->将请求头、请求体数据，放入HttpEntity中
        // 请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
        // 这里手写了一个json串作为请求体 数据 (实际开发时,可使用fastjson、gson等工具将数据转化为json串)
        String httpBody = "{\"motto\":\"唉呀妈呀！脑瓜疼！\"}";
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);

        // -------------------------------> URI
        StringBuffer paramsURL = new StringBuffer("http://127.0.0.1:9527/restTemplate/doHttpPost");
        // 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
        paramsURL.append("?flag=" + URLEncoder.encode("&", "utf-8"));
        URI uri = URI.create(paramsURL.toString());

        //  -------------------------------> 执行请求并返回结果
        // 此处的泛型  对应 响应体数据   类型;即:这里指定响应体的数据装配为String
        ResponseEntity<String> response =
                restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        // -------------------------------> 响应信息
        //响应码,如:401、302、404、500、200等
        System.err.println(response.getStatusCodeValue());
        Gson gson = new Gson();
        // 响应头
        System.err.println(gson.toJson(response.getHeaders()));
        // 响应体
        if(response.hasBody()) {
            System.err.println(response.getBody());
        }

    }












    /**
     * RestTemplate 发送  HTTPs POST请求 --- 测试
     * @throws UnsupportedEncodingException
     *
     * @date 2018年9月8日 下午2:12:50
     */
    public String doHttpsPostTest() throws UnsupportedEncodingException {
        // -------------------------------> 获取Rest客户端实例
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());

        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1); // 移除原来的转换器
        // 设置字符编码为utf-8
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
        restTemplate.setMessageConverters(converterList);

        // -------------------------------> (选择性设置)请求头信息
        // HttpHeaders实现了MultiValueMap接口
        HttpHeaders httpHeaders = new HttpHeaders();
        // 设置contentType
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        // 给请求header中添加一些数据
        httpHeaders.add("JustryDeng", "这是一个大帅哥!");

        // ------------------------------->将请求头、请求体数据，放入HttpEntity中
        // 请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
        // 这里手写了一个json串作为请求体 数据 (实际开发时,可使用fastjson、gson等工具将数据转化为json串)
        //将请求头部和参数合成一个请求
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String url = "https://bufpay.com/api/pay/97102";
        params.add("name", "product1");
        params.add("pay_type", "alipay");
        params.add("price", "0");
        params.add("order_uid", "user001");
        params.add("notify_url", "http://www.baidu.com");
        params.add("return_url", "http://www.baidu.com");
        params.add("feedback_url", "");
        params.add("secret", "a0e8f0bc942c44c3ac1f1d438830fa33");
        params.add("order_id", "1212");
        params.add("sign", "E098F014BEB67E0E1DF716A6218B39A7");

        String httpBody = "{\"motto\":\"唉呀妈呀！脑瓜疼！\"}";
//        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, httpHeaders);
        // -------------------------------> URI
        StringBuffer paramsURL = new StringBuffer("https://bufpay.com/api/pay/97102");
        // 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
        paramsURL.append("?flag=" + URLEncoder.encode("&", "utf-8"));
        URI uri = URI.create(paramsURL.toString());

        //  -------------------------------> 执行请求并返回结果
        // 此处的泛型  对应 响应体数据   类型;即:这里指定响应体的数据装配为String
        ResponseEntity<String> response =
                restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        // -------------------------------> 响应信息
        //响应码,如:401、302、404、500、200等
        System.err.println(response.getStatusCodeValue());
        Gson gson = new Gson();
        // 响应头
        System.err.println(gson.toJson(response.getHeaders()));
        // 响应体
        if(response.hasBody()) {
            System.err.println(response.getBody());
            return response.getBody();
        }
        return "";

    }

    /**
     * RestTemplate 发送  HTTP GET请求 --- 测试
     * @throws UnsupportedEncodingException
     *
     * @date 2018年7月13日 下午4:18:50
     */
    public void doHttpGetTest() throws UnsupportedEncodingException {
        // -------------------------------> 获取Rest客户端实例
        RestTemplate restTemplate = new RestTemplate();

        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
        converterList.remove(1); // 移除原来的转换器
        // 设置字符编码为utf-8
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
        restTemplate.setMessageConverters(converterList);

        // -------------------------------> (选择性设置)请求头信息
        // HttpHeaders实现了MultiValueMap接口
        HttpHeaders httpHeaders = new HttpHeaders();
        // 给请求header中添加一些数据
        httpHeaders.add("JustryDeng", "这是一个大帅哥!");

        // -------------------------------> 注:GET请求 创建HttpEntity时,请求体传入null即可
        // 请求体的类型任选即可;只要保证 请求体 的类型与HttpEntity类的泛型保持一致即可
        String httpBody = null;
        HttpEntity<String> httpEntity = new HttpEntity<String>(httpBody, httpHeaders);

        // -------------------------------> URI
        StringBuffer paramsURL = new StringBuffer("http://127.0.0.1:9527/restTemplate/doHttpGet");
        // 字符数据最好encoding一下;这样一来，某些特殊字符才能传过去(如:flag的参数值就是“&”,不encoding的话,传不过去)
        paramsURL.append("?flag=" + URLEncoder.encode("&", "utf-8"));
        URI uri = URI.create(paramsURL.toString());

        //  -------------------------------> 执行请求并返回结果
        // 此处的泛型  对应 响应体数据   类型;即:这里指定响应体的数据装配为String
        ResponseEntity<String> response =
                restTemplate.exchange(uri, HttpMethod.GET, httpEntity, String.class);

        // -------------------------------> 响应信息
        //响应码,如:401、302、404、500、200等
        System.err.println(response.getStatusCodeValue());
        Gson gson = new Gson();
        // 响应头
        System.err.println(gson.toJson(response.getHeaders()));
        // 响应体
        if(response.hasBody()) {
            System.err.println(response.getBody());
        }

    }

}
