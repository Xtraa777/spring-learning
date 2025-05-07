package com.xtraa.springlearning.service;

import com.xtraa.springlearning.component.MessageFormatter; // MessageFormatter 임포트
import lombok.RequiredArgsConstructor; // Lombok 임포트
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // Lombok: final 필드에 대한 생성자를 자동 생성하여 생성자 주입을 간결하게 함
public class GreetingServiceImpl implements GreetingService {

    private final MessageFormatter messageFormatter; // final 필드 선언 -> 생성자 주입 대상

    // @RequiredArgsConstructor가 아래 생성자 코드를 자동 생성합니다.
    /*
    @Autowired // 생성자가 하나면 생략 가능 (Spring 4.3+)
    public GreetingServiceImpl(MessageFormatter messageFormatter) {
        this.messageFormatter = messageFormatter;
    }
    */

    @Override
    public String greet(String name) {
        String baseGreeting;
        if (name == null || name.isEmpty()) {
            baseGreeting = "Hello, World!";
        } else {
            baseGreeting = "Hello, " + name + "!";
        }
        // 주입받은 MessageFormatter Bean을 사용하여 메시지 포맷팅
        return messageFormatter.format(baseGreeting);
    }
}
