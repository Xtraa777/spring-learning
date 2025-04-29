package com.xtraa.springlearning.controller;

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

    @PostMapping("/request-body/products")
    public String handleRequestBody(@RequestBody Product product) {
        // 요청 본문: {"name": "Laptop", "price": 1200.0}
        // -> Spring 이 Product 객체 생성 후 name 필드에 "Laptop", price 필드에 1200.0 설정

        return "Received Product from body: " + product.getName() + " (" + product.getPrice() + ")";
    }

    @PostMapping("/response-entity/products")
    public ResponseEntity<Product> createProductWithResponseEntity(@RequestBody Product product) {
        System.out.println("POST /response-entity/products 요청 받음: " + product.getName());

        Product createdProduct = product;

        // HTTP 상태 코드 201 Created 와 함께 생성된 Product 객체를 응답 본문에 담아 반환
        // 헤더 추가 등 더 많은 제어 가능
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
        // return new ResponseEntity<>(createdProduct, HttpStatus.CREATED); // 위와 동일한 결과
    }

    @GetMapping("/response-entity/product/{productId}")
    public ResponseEntity<?> getProductResponseEntity(@PathVariable Long productId) {

        // 조회 결과가 없다고 가정
        if (productId > 100) {
            // HTTP 상태 코드 404 Not Found 와 함께 응답 본문 없이 반환
            return ResponseEntity.notFound().build();
            // return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 동일 결과
        }
        // 조회 결과가 있다고 가정
        Product foundProduct = new Product("Sample Product", 99.99);

        return ResponseEntity.ok(foundProduct);
        // return ResponseEntity.status(HttpStatus.OK).body(foundProduct); // 동일 결과
    }
}