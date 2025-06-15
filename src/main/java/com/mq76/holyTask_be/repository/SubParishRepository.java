package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.SubParish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubParishRepository extends JpaRepository<SubParish, Integer> {
    List<SubParish> findByParish_Id(Integer parishId);
    @Query(value = "select * from sub_parishes", nativeQuery = true)
    List<SubParish> findAllSubParishes();
}

