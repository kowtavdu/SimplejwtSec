package org.example.controller;

import org.example.entity.UserInfo;
import org.example.service.UserDetailUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserDetailUserInfo userDetailUserInfo;
    @GetMapping("/welcome")
    public String welcome(){
        return "welcome page";
    }

    @GetMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String products(){
        return "product page";
    }

    @GetMapping("/products/id")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String products(@PathVariable int id){
        return id+" product page";
    }
    @PostMapping("/createUser")
    public String createUser(@RequestBody UserInfo userInfo){
        userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
        userDetailUserInfo.createUser(userInfo);
        return "user created successfully";
    }
}
