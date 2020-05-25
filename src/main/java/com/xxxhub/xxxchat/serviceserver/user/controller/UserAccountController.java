package com.xxxhub.xxxchat.serviceserver.user.controller;

import com.xxxhub.xxxchat.serviceserver.user.model.dto.AccountLoginDTO;
import com.xxxhub.xxxchat.serviceserver.user.model.dto.AccountRegisterDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * * HT
 * * 2020/5/24
 **/
@RestController
@RequestMapping("/api/account")
public class UserAccountController {

    @PostMapping("/register")
    public Object register(@RequestBody AccountRegisterDTO accountRegisterDTO){

        return null;
    }

    @PostMapping("/login")
    public Object login(@RequestBody AccountLoginDTO accountLoginDTO){

        return null;
    }
}
