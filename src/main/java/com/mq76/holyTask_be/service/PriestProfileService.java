package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.EditProfile;
import com.mq76.holyTask_be.model.PriestProfile;
import com.mq76.holyTask_be.model.ResponseObject;

import java.util.List;
import java.util.Optional;

public interface PriestProfileService {
    ResponseObject createProfile(PriestProfile profile);
    ResponseObject getByUserId(Integer userId);
    ResponseObject getByParishId(Integer parishId);
    ResponseObject updateProfile(EditProfile editProfile, Integer userId);
}
