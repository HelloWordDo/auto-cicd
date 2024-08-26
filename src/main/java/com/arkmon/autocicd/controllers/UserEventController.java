package com.arkmon.autocicd.controllers;

import Exceptions.MAException;
import ReturnEntities.ReturnData;
import com.arkmon.autocicd.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author X.J
 * @date 2021/2/4
 */
@RestController
@RequestMapping("/user")
@Scope("request")
public class UserEventController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String register(String username){
        userService.register(username);
        return "恭喜注册成功!";
    }


}
