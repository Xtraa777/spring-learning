package com.xtraa.springlearning.controller;

import com.xtraa.springlearning.dto.ProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataHandlingController {

    @GetMapping("/request-param")
    public String handleRequestParam(
        @RequestParam("name") String userName, // @RequestParam("요청 파라미터 이름") 타입 변수이름
        @RequestParam(value = "age", required = false, defaultValue = "0") int userAge) {

        // 요청: /request-param?name=Alice&age=30  -> userName="Alice", userAge=30
        // 요청: /request-param?name=Bob          -> userName="Bob", userAge=0 (age 없으므로 기본값)
        // 요청: /request-param?age=25            -> 오류 발생 (name 파라미터 필수인데 누락됨)

        return "Received Name: " + userName + ", Age: " + userAge;
    }

    // 파라미터 이름과 변수 이름이 같으면 어노테이션 값 생략 가능
    @GetMapping("/simple-param")
    public String handleSimpleParam(@RequestParam String query) {

        // 요청: /simple-param?query=스프링부트 -> query="스프링부트"
        return "Received Query: " + query;
    }

    @GetMapping("/path-variable/users/{userId}")
    public String handlePathVariable(
        @PathVariable("userId") Long userId) {

        return "Received User ID from path: " + userId;
    }

    // 경로 변수 이름과 파라미터 이름이 같으면 어노테이션 값 생략 가능
    @GetMapping("/path-variable/products/{productId}/details")
    public String handleMultiPathVariable(@PathVariable Long productId) {
        // 요청: /path-variable/products/abc/details -> productId="abc" (타입 일치 중요)
        // Long 타입으로 받았으므로 "abc" 요청 시 오류 발생, 숫자만 가능

        return "Received Product ID from path: " + productId;
    }

    // POST /request-body/products 요청 처리
// @RequestBody 로 받을 타입을 ProductEntity 가 아닌 ProductDto 로 변경
    @PostMapping("/request-body/products")
    public String handleRequestBody(@RequestBody ProductDto productDto) {
        System.out.println(
            "Received ProductDto from body: " + productDto.getName() + " (" + productDto.getPrice()
                + ")");
        // 받은 DTO 객체를 사용하여 비즈니스 로직(Service 호출 등) 수행...
        // Service 계층에서 ProductEntity 로 변환하여 DB에 저장할 수 있음
        return "Received ProductDto: " + productDto.getName();
    }

    // createProductWithResponseEntity 메서드도 ProductEntity 대신 ProductDto 를 사용하거나,
    // 응답용 DTO (예: CreatedProductResponseDto) 를 새로 만들어 사용하는 것이 좋습니다.
    // 일단 간단하게 ProductDto 를 사용하도록 수정합니다.
    @PostMapping("/response-entity/products")
    public ResponseEntity<ProductDto> createProductWithResponseEntity(
        @RequestBody ProductDto productDto) {
        System.out.println("POST /response-entity/products 요청 받음: " + productDto.getName());

        // 비즈니스 로직 수행 (Service 호출 등) ...
        // 응답용 DTO 를 만들어 반환하는 것이 일반적이지만, 여기서는 간단히 받은 DTO 를 그대로 반환
        ProductDto createdProductResponse = productDto; // 실제로는 저장 후 응답에 필요한 정보만 담은 DTO

        return ResponseEntity.status(HttpStatus.CREATED).body(createdProductResponse);
    }

    @GetMapping("/response-entity/product/{productId}")
    public ResponseEntity<ProductDto> getProductResponseEntity(
        @PathVariable Long productId) { // 반환 타입을 ResponseEntity<ProductDto> 로 변경

        // 실제로는 Service 계층 호출하여 DB에서 productId 로 Product Entity 를 조회하고,
        // 그 Entity 를 ProductDto 로 변환하여 반환해야 합니다.
        // 여기서는 간단한 예시를 위해 임의의 DTO 객체를 생성합니다.

        // 조회 결과가 없다고 가정
        if (productId > 100) { // 예시 조건
            // HTTP 상태 코드 404 Not Found 와 함께 응답 본문 없이 반환
            return ResponseEntity.notFound().build();
        }

        // 조회 결과가 있다고 가정하고 임의의 ProductDto 객체 생성
        // ProductEntity 가 아님! 클라이언트에게 보여줄 DTO 객체입니다.
        ProductDto foundProductDto = new ProductDto("Sample Product",
            99.99); // ProductDto 객체 생성 및 값 설정

        // HTTP 상태 코드 200 OK 와 함께 ProductDto 객체를 응답 본문에 담아 반환
        // Spring 이 ProductDto 객체를 JSON 으로 자동 변환합니다.
        return ResponseEntity.ok(foundProductDto);
    }
}