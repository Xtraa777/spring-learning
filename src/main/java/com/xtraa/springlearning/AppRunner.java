package com.xtraa.springlearning;

import com.xtraa.springlearning.service.GreetingService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// @Component: 이 클래스도 Spring Bean으로 등록
@Component
@RequiredArgsConstructor // final 필드인 greetingService와 appConfigText에 대한 생성자 주입
public class AppRunner implements CommandLineRunner {

    // 생성자 주입: Spring 컨테이너로부터 GreetingService 타입의 Bean을 주입받음
    private final GreetingService greetingService; // GreetingServiceImpl 객체가 주입될 것임 (인터페이스 타입으로 받는 것이 좋음)

    // 생성자 주입: Spring 컨테이너로부터 String 타입의 "appConfigText" Bean을 주입받음
    // @Qualifier("appConfigText") // String Bean이 여러 개일 경우 명확히 지정 (현재는 하나뿐이니 생략 가능)
    private final String appConfigText;


    // Spring Boot 애플리케이션 실행 완료 후 자동으로 호출되는 메서드
    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n--- Application Runner 실행 ---");

        // 주입받은 GreetingService Bean 사용
        String greeting = greetingService.greet("Spring User");
        System.out.println("GreetingService 결과: " + greeting);

        // 주입받은 @Bean 문자열 사용
        System.out.println("@Bean으로 등록된 문자열: " + appConfigText);

        System.out.println("--- Application Runner 종료 ---\n");

        // 애플리케이션을 계속 실행 상태로 두거나, 여기서 종료하거나 선택 가능
        // System.exit(0); // 콘솔 앱처럼 즉시 종료하려면 사용
    }
}
