package com.xtraa.springlearning.repostiory;

import com.xtraa.springlearning.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // @Repository // 선택적 어노테이션
    // JpaRepository<User, Long> 를 상속받은 상태

    // --- 쿼리 메서드 (Derived Query Methods) 예시 ---

    // 사용자 이름으로 조회 (WHERE username = ?)
    Optional<User> findByUsername(String username); // 결과가 0 또는 1개일 경우 Optional 반환

    // 이메일로 조회 (WHERE email = ?)
    User findByEmail(String email); // 결과가 반드시 1개일 경우 (unique 등) 사용 가능, 없으면 null 반환

    // 사용자 이름과 이메일로 조회 (WHERE username = ? AND email = ?)
    Optional<User> findByUsernameAndEmail(String username, String email);

    // 나이가 특정 값보다 큰 모든 사용자 조회 (WHERE age > ?)
    List<User> findByAgeGreaterThan(int age);

    // 사용자 이름에 특정 문자열이 포함된 사용자 조회 (WHERE username LIKE %?%)
    List<User> findByUsernameContaining(String keyword);

    // 나이가 특정 범위에 있는 사용자 조회 (WHERE age BETWEEN ? AND ?)
    List<User> findByAgeBetween(int startAge, int endAge);

    // 특정 이름을 가진 사용자가 존재하는지 확인 (SELECT COUNT(*) WHERE username = ?)
    boolean existsByUsername(String username);

    // 이메일 끝이 특정 문자열로 끝나는 모든 사용자 조회, 나이 내림차순 정렬 (WHERE email LIKE %? ORDER BY age DESC)
    List<User> findByEmailEndingWithOrderByAgeDesc(String emailSuffix);

    // --- JPQL (@Query) 예시 ---

    // JPQL: 나이가 특정 값보다 크거나 같고 특정 이메일 패턴을 포함하는 모든 사용자 조회
    // FROM User u : User Entity 를 u 라는 별칭으로 사용
    // WHERE u.age >= :minAge AND u.email LIKE %:emailKeyword% : 조건절 (필드 이름 사용)
    // @Param("파라미터이름"): 메서드 파라미터와 JPQL 쿼리 안의 파라미터 이름을 연결
    @Query("SELECT u FROM User u WHERE u.age >= :minAge AND u.email LIKE %:emailKeyword%")
    List<User> findUsersByAgeAndEmailKeyword(@Param("minAge") int minAge, @Param("emailKeyword") String emailKeyword);

    // JPQL: 특정 사용자 이름으로 조회 (쿼리 메서드와 동일한 기능)
    // @Query("SELECT u FROM User u WHERE u.username = :username")
    // Optional<User> findByUsernameJPQL(@Param("username") String username);

    // 네이티브 SQL 사용 예시 (MySQL 문법 그대로 사용)
    // @Query(value = "SELECT * FROM users u WHERE u.username LIKE :usernamePattern", nativeQuery = true)
    // List<User> findUsersByUsernameNative(@Param("usernamePattern") String usernamePattern);

    // JPQL: 특정 컬럼(들)만 선택하여 DTO로 직접 받는 예시 (Projection) - 복잡하므로 개념만 소개
    // @Query("SELECT new com.yourname.study.dto.UserSimpleDto(u.username, u.email) FROM User u WHERE u.age > 20")
    // List<UserSimpleDto> findUserSimpleDtoByAgeGreaterThan(int age);
    // (UserSimpleDto 클래스 필요, DTO 클래스에 해당 필드를 받는 생성자 필요)
}
