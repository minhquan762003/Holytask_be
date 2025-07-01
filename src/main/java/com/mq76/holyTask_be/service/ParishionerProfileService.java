package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.ParishionerProfile;
import com.mq76.holyTask_be.model.ParishionerRequest;
import com.mq76.holyTask_be.model.ResponseObject;

import java.util.List;
import java.util.Optional;

public interface ParishionerProfileService {
    ResponseObject createParishioner(ParishionerRequest parishionerRequest);
    ResponseObject updateParishioner(ParishionerRequest parishionerRequest);
//    ResponseObject getByUserId(Integer userId);
    ResponseObject getBySubParishId(Integer subParishId);
    ResponseObject getByParishId(Integer parishId);
    ResponseObject findParishioners(Integer parishId, Integer groupId, String name);
    ResponseObject deleteParishioner(Integer parishId);
}

