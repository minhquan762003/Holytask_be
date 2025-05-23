package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.SubParish;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubParishRepository extends JpaRepository<SubParish, Integer> {
    List<SubParish> findByParish_Id(Integer parishId);
}

