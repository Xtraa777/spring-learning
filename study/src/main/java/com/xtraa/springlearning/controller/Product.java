package com.xtraa.springlearning.controller;

import com.xtraa.springlearning.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double price;

    // --- 연관관계 매핑 추가 ---

    // @ManyToOne: Product (N) 와 User (1) 의 N:1 관계에서 N 쪽에 해당
    // fetch = FetchType.LAZY: User 객체를 실제로 사용할 때까지 로딩을 지연 (기본값)
    // @JoinColumn: 외래 키 컬럼 설정 (name="컬럼명", referencedColumnName="참조하는 컬럼명")
    // nullable = false: 외래 키가 NULL 을 허용하지 않음 (Product 는 반드시 User 에 속해야 함)
    @ManyToOne(fetch = FetchType.LAZY) // N:1 관계에서는 기본 fetch 타입이 EAGER 이지만, 성능을 위해 LAZY 로 명시하는 것이 좋음
    @JoinColumn(name = "user_id") // products 테이블에 user_id 라는 이름의 외래 키 컬럼 생성
    private User user; // 참조할 User Entity 필드

    // Lombok @AllArgsConstructor 사용 시 주의: user 필드가 초기화되지 않은 상태로 생성될 수 있음
}