package com.xtraa.springlearning.service;

import com.xtraa.springlearning.entity.User;
import com.xtraa.springlearning.repostiory.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User registerUser(User user) {
        System.out.println("UserService: 사용자 등록 시도 - " + user.getUsername());
        // userRepository.save() 메서드 호출 -> Spring Data JPA가 INSERT SQL 자동 생성/실행
        User savedUser = userRepository.save(user);
        System.out.println("UserService: 사용자 등록 완료 - ID: " + savedUser.getId());
        return savedUser;
    }

    // 전체 사용자 조회 (SELECT *)
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        System.out.println("UserService: 전체 사용자 조회");
        // userRepository.findAll() 메서드 호출 -> Spring Data JPA가 SELECT * FROM users SQL 자동 생성/실행
        return userRepository.findAll();
    }

    // ID로 사용자 조회 (SELECT * WHERE id = ?)
    public Optional<User> findUserById(Long id) {
        System.out.println("UserService: ID로 사용자 조회 - " + id);
        // userRepository.findById() 메서드 호출 -> Spring Data JPA가 SELECT * FROM users WHERE id = ? SQL 자동 생성/실행
        return userRepository.findById(id); // 결과가 없을 수 있으므로 Optional 반환
    }

    // 사용자 삭제 (DELETE)
    @Transactional // (다음 시간 상세 설명) 쓰기 작업이므로 트랜잭션 필요
    public void deleteUser(Long id) {
        System.out.println("UserService: 사용자 삭제 시도 - " + id);
        // userRepository.deleteById() 메서드 호출 -> Spring Data JPA가 DELETE FROM users WHERE id = ? SQL 자동 생성/실행
        userRepository.deleteById(id);
        System.out.println("UserService: 사용자 삭제 완료 - " + id);
    }

    // 여러 DB 작업을 포함하는 트랜잭션 예시
    @Transactional // 이 메서드 안의 모든 DB 작업은 하나의 트랜잭션으로 묶임
    public void updateUserEmailAndAge(Long id, String newEmail, int newAge) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("사용자 없음")); // 사용자 조회
        user.setEmail(newEmail); // 이메일 수정 (영속성 컨텍스트 1차 캐시에 반영)
        user.setAge(newAge);     // 나이 수정 (영속성 컨텍스트 1차 캐시에 반영)

        // save 메서드를 명시적으로 호출할 필요 없이, @Transactional 메서드가 종료되는 시점에
        // 영속성 컨텍스트의 변경 내용을 데이터베이스에 자동으로 반영 (Dirty Checking)
        System.out.println("UserService: 사용자 정보 업데이트 시도 - ID: " + id);
        // userRepository.save(user); // 명시적으로 호출해도 되지만, Dirty Checking 으로 자동 업데이트 됨
        System.out.println("UserService: 사용자 정보 업데이트 완료 - ID: " + id);

        // 예시: 업데이트 중간에 오류 발생 시, 이 메서드 시작부터 모든 DB 작업(조회 포함)이 롤백되어 변경 사항이 DB에 반영되지 않습니다.
        // if (newAge < 0) {
        //    throw new IllegalArgumentException("나이는 음수일 수 없습니다.");
        // }
    }
}
