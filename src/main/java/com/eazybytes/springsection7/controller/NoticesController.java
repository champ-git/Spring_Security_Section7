package com.eazybytes.springsection7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NoticesController {

    @GetMapping("/myNotices")
    public String getNoticesDetails(){
        return "Here are the Notices details from DB";
    }

}
