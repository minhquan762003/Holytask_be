package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.Report;
import com.mq76.holyTask_be.model.ReportType;
import com.mq76.holyTask_be.model.ResponseObject;

import java.util.List;

public interface ReportService {
    ResponseObject createOrUpdateReport(Report report);
    ResponseObject findByPriest_Id(Integer priestId);
    ResponseObject findByType(ReportType type);
}
