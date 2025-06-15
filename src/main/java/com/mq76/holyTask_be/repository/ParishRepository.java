package com.mq76.holyTask_be.repository;
import com.mq76.holyTask_be.model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParishRepository extends JpaRepository<Parish, Integer> {
    List<Parish> findByNameContainingIgnoreCase(String name);
    @Query(value = "select * from parishes where id = :parishId", nativeQuery = true)
    Optional<Parish> finbyParishId(@Param(value = "parishId") Integer parishId);
}

