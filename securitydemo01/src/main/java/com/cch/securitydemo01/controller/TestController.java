package com.cch.securitydemo01.controller;

import java.util.ArrayList;
import java.util.List;

import com.cch.securitydemo01.entity.User;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {
    @GetMapping("/hello")
    public String hello() {
        return "Hello Security";
    }

    @GetMapping("/index")
    public String index() {
        return "Hello index";
    }

    @GetMapping("/update")
    @Secured(value = {"ROLE_sale","ROLE_manager"})
    public String update() {
        return "Hello update";
    }

    @GetMapping("/preAuth")
    @PreAuthorize(value = "hasAnyAuthority('admins')")
    public String preAuth() {
        return "Hello preAuth";
    }

    @GetMapping("/postAuth")
    @PostAuthorize(value = "hasAnyAuthority('admins')")
    public String postAuth() {
        System.out.println("update...");
        return "Hello postAuth";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ROLE_manager')")
    @PostFilter("filterObject.username == 'admin1'")
    public List<User> getUserList() {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "admin1", "password"));
        list.add(new User("2", "admin2", "password"));

        return list;
    }

    @GetMapping("/prefilter/list")
    @PreAuthorize("hasRole('ROLE_manager')")
    @PreFilter("filterObject.username == 'admin1'")
    public List<User> getUserListForPreFilter(@RequestBody List<User> list) {
        list.forEach(System.out::println);
        return list;
    }
}
