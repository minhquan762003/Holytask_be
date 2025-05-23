package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MassSchedule;
import com.mq76.holyTask_be.model.ResponseObject;

import java.time.LocalDateTime;
import java.util.List;


public interface MassScheduleService {
    ResponseObject createOrUpdateMass(MassSchedule mass);
    ResponseObject getByPriestId(Integer priestId);
    ResponseObject findByDatetimeBetween(LocalDateTime from, LocalDateTime to);
}

