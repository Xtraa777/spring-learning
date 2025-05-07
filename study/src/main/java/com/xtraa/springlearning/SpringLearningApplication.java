package com.xtraa.springlearning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringLearningApplication {

    public static void main(String[] args) {
        // SpringApplication.run() 메서드가 Spring IoC 컨테이너(ApplicationContext)를 실행하고 초기화
        ApplicationContext context = SpringApplication.run(
            SpringLearningApplication.class, args);

        System.out.println("Spring IoC 컨테이너가 초기화되었습니다.");
        // 컨테이너가 관리하는 Bean의 개수를 확인해보자 (IoC 컨테이너가 Bean을 관리한다는 것을 보여줌)
        System.out.println("컨테이너가 관리하는 Bean의 개수: " + context.getBeanDefinitionCount());

        // 나중에 우리가 등록한 Bean을 여기서 꺼내서 확인해 볼 수도 있습니다.
        // 예: String beanName = context.getBean(String.class);
        // System.out.println("@Bean으로 등록된 문자열 Bean: " + beanName); // 아직 이 Bean은 없음

        // Spring 애플리케이션은 main 메서드가 종료되어도 컨테이너가 살아있으면서 요청을 대기합니다.
    }

}
