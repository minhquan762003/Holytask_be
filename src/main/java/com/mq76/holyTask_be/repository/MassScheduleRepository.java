package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.MassSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface MassScheduleRepository extends JpaRepository<MassSchedule, Integer> {
    List<MassSchedule> findByParish_Id(Integer parishId);

    List<MassSchedule> findByPriest_Id(Integer priestId);

    List<MassSchedule> findByDatetimeBetween(LocalDateTime from, LocalDateTime to);
}
