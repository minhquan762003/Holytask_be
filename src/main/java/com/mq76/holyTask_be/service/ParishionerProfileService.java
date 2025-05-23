package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.ParishionerProfile;
import com.mq76.holyTask_be.model.ResponseObject;

import java.util.List;
import java.util.Optional;

public interface ParishionerProfileService {
    ResponseObject createOrUpdateProfile(ParishionerProfile profile);
    ResponseObject getByUserId(Integer userId);
    ResponseObject getBySubParishId(Integer subParishId);
}

