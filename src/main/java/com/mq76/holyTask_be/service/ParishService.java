package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ResponseObject;

import java.util.List;

public interface ParishService {
    ResponseObject createOrUpdateParish(Parish parish);
    ResponseObject findByNameContainingIgnoreCase(String name);
}
