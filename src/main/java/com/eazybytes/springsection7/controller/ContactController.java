package com.eazybytes.springsection7.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    @GetMapping("/myContact")
    public String getContactDetails(){
        return "Here are the Contacts details from DB";
    }

}
