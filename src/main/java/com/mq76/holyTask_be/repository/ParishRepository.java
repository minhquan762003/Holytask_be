package com.mq76.holyTask_be.repository;
import com.mq76.holyTask_be.model.Parish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ParishRepository extends JpaRepository<Parish, Integer> {
    List<Parish> findByNameContainingIgnoreCase(String name);
}

