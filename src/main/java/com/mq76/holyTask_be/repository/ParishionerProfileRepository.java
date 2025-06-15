package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.ParishionerProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParishionerProfileRepository extends JpaRepository<ParishionerProfile, Integer> {
//    Optional<ParishionerProfile> findByUser_Id(Integer userId);
    List<ParishionerProfile> findBySubParish_Id(Integer subParishId);
    Optional<ParishionerProfile> findByPhoneNumber(String phoneNumber);
}
