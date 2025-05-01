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
}
