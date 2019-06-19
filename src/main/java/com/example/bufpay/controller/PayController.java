package com.example.bufpay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author sonny
 * @Description
 * @Date 2019/6/19 15:04
 **/
@Controller
public class PayController {

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String pay() {
        return "a";
    }

}
