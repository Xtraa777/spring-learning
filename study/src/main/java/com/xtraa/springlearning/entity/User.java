package com.xtraa.springlearning.entity;


import com.xtraa.springlearning.controller.Product;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id // 기본 키 (Primary Key)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column
    private int age;

    // --- 연관관계 매핑 추가 ---

    // @OneToMany: User (1) 와 Product (N) 의 1:N 관계에서 1 쪽에 해당
    // mappedBy: 연관관계의 주인이 아님. Product Entity 에 있는 'user' 필드에 의해 매핑됨을 명시
    // cascade = CascadeType.ALL: User 가 변경/삭제될 때 연관된 Product 도 함께 처리 (All은 너무 광범위, MERGE, PERSIST 등 선택적 사용 권장)
    // orphanRemoval = true: User 와의 연관관계가 끊어진 Product Entity 를 자동으로 삭제
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> likedProducts = new ArrayList<>(); // 1:N 관계의 N 쪽에 해당하는 컬렉션 필드

    // 양방향 관계 편의 메서드 (주인 객체와 주인이 아닌 객체 모두에 관계 설정)
    public void addLikedProduct(Product product) {
        this.likedProducts.add(product); // User -> Product 관계 설정
        if (product.getUser() != this) {
            product.setUser(this); // Product -> User 관계 설정 (무한 루프 방지 체크)
        }
    }

    public void removeLikedProduct(Product product) {
        this.likedProducts.remove(product); // User -> Product 관계 제거
        if (product.getUser() == this) {
            product.setUser(null); // Product -> User 관계 제거
        }
    }

    // Lombok @AllArgsConstructor 사용 시 주의: 컬렉션 필드는 초기화되지 않은 상태로 생성될 수 있음 -> 빌더 패턴 또는 별도 생성자 고려
}
