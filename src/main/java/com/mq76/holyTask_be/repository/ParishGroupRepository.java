package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ParishGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParishGroupRepository extends JpaRepository<ParishGroup, Integer> {
    @Query(value = "select * from parish_groups", nativeQuery = true)
    List<ParishGroup> findAllGroups();
}
