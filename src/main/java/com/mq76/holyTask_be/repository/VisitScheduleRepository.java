package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.VisitSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface VisitScheduleRepository extends JpaRepository<VisitSchedule, Integer> {
    List<VisitSchedule> findByPriest_Id(Integer priestId);
    List<VisitSchedule> findByDatetimeBetween(Date from, Date to);
    Optional<VisitSchedule> findById(Integer id);
    void deleteById(Integer id);
    @Query(value = "SELECT * FROM visit_schedules vs WHERE vs.priest_id = :priestId AND DATE(vs.datetime) = STR_TO_DATE(:targetDate, '%d/%m/%Y')", nativeQuery = true)
    List<VisitSchedule> findByPriestIdAndDate(
            @Param("priestId") Integer priestId,
            @Param("targetDate") String targetDate
    );

    @Query(value = "select * from visit_schedules where DATE(visit_schedules.datetime) = STR_TO_DATE(:targetDate, '%d/%m/%Y')", nativeQuery = true)
    List<VisitSchedule> findVisitByCurrentDate(@Param("targetDate") String targetDate);

}
