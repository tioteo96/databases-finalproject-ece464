package com.packet.indoor.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UiController {

    @GetMapping(value = "/")
    public String landing(){
        return "redirect:login";
    }
    
    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/control")
    // @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String control(){
        return "control";
    }
}
