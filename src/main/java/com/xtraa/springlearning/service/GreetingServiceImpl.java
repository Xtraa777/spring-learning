package com.xtraa.springlearning.service;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    public String greet(String name) {
        if (name == null || name.isEmpty()) {
            return "Hello, World!";
        }
        return "Hello, " + name + "!";
    }
}
