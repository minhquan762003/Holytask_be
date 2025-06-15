package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ParishGroup;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.SubParish;
import com.mq76.holyTask_be.repository.ParishGroupRepository;
import com.mq76.holyTask_be.repository.SubParishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class SubParishServiceImpl implements SubParishService {
    private final SubParishRepository subParishRepository;
    @Override
    public ResponseObject createOrUpdateSubParish(SubParish subParish) {
        try {
            subParishRepository.save(subParish);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG,subParish);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject findByParish_Id(Integer parishId) {
        try {
            subParishRepository.findByParish_Id(parishId);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,subParishRepository.findByParish_Id(parishId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject findAllSubParishes() {
        try{
            List<SubParish> subParish = this.subParishRepository.findAllSubParishes();
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,subParish);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Autowired
    ParishGroupRepository parishGroupRepository;
    @Override
    public ResponseObject findAllGroups() {
        try{
            List<ParishGroup> parishGroups = this.parishGroupRepository.findAllGroups();
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,parishGroups);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }
}
