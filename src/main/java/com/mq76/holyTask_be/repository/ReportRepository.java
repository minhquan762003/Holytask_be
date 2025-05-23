package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Report;
import com.mq76.holyTask_be.model.ReportType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Integer> {
    List<Report> findByPriest_Id(Integer priestId);
    List<Report> findByType(ReportType type);

}
