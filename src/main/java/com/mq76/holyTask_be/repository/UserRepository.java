package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Role;
import com.mq76.holyTask_be.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<User> findByRole(Role role);

    @Query(value = "select * from users where id = :userId", nativeQuery = true)
    Optional<User> findUserById(@Param("userId") Integer userId);

    @Query(value = "select * from users where role = 'PRIEST'", nativeQuery = true)
    List<User> findAllUsers();
}

