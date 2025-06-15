package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.SubParish;

import java.util.List;

public interface SubParishService {
    ResponseObject createOrUpdateSubParish(SubParish subParish);
    ResponseObject findByParish_Id(Integer subParishId);
    ResponseObject findAllSubParishes();
    ResponseObject findAllGroups();
}
