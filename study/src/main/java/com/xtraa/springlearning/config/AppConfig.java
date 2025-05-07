package com.xtraa.springlearning.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration: 이 클래스가 Spring Bean 설정을 담고 있는 설정 클래스임을 알림
@Configuration
public class AppConfig {

    // @Bean: 이 메서드가 반환하는 객체(여기서는 문자열 "Application Configuration Text")를
    // Spring 컨테이너에게 Bean으로 등록하라고 알림
    // Bean의 이름은 기본적으로 메서드 이름인 "appConfigText"가 됩니다.
    @Bean
    public String appConfigText() {
        System.out.println("@Bean 메서드 호출: 'appConfigText' Bean 생성");
        return "Application Configuration Text";
    }

    // 다른 Bean을 등록하는 @Bean 메서드를 추가할 수 있습니다.
    // 예: 외부 라이브러리 클래스의 객체를 Bean으로 등록
    /*
    @Bean
    public ExternalLibraryClass externalBean() {
        return new ExternalLibraryClass();
    }
    */
}
