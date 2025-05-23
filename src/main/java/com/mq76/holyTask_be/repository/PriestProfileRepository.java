package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.PriestProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PriestProfileRepository extends JpaRepository<PriestProfile, Integer> {
    Optional<PriestProfile> findByUser_Id(Integer userId);
    List<PriestProfile> findByParish_Id(Integer parishId);
    @Query(value = "select priest_profiles.id from priest_profiles", nativeQuery = true)
    List<Integer> findAllPriestIds();
}
