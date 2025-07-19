package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.UserPrincipal;

import java.util.List;

public interface ParishService {
    ResponseObject createParish(Parish parish, UserPrincipal user);
    ResponseObject findByNameContainingIgnoreCase(String name);
    ResponseObject finbyParishId(Integer parishId);
    ResponseObject getAllParish();
    ResponseObject updateParish(Parish parish, UserPrincipal user);
    ResponseObject findbyIsSelected();
    ResponseObject deleteParish(Integer parishId);
}
