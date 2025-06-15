package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.VisitSchedule;


import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface VisitScheduleService {
    ResponseObject createScheduleVisit(VisitSchedule visit);
    ResponseObject getVisitsByPriestId(Integer priestId);
    ResponseObject findByDatetimeBetween(Date from, Date to);
    ResponseObject updateScheduleVisit(VisitSchedule visit, Integer id);
    ResponseObject findById(Integer id);
    ResponseObject sendEmailNotification(Integer idVisit, String userEmail);
    ResponseObject deleteScheduleVisit(Integer id);
    ResponseObject getVisitByDate(String strDate);
}

