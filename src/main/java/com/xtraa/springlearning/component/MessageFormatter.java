package com.xtraa.springlearning.component;

import org.springframework.stereotype.Component;

@Component
public class MessageFormatter {

    public String format(String message) {
        return "---" + message + "---";
    }
}
