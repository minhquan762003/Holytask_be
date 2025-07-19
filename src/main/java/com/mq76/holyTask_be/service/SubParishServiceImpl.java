package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.*;
import com.mq76.holyTask_be.repository.ParishGroupRepository;
import com.mq76.holyTask_be.repository.ParishRepository;
import com.mq76.holyTask_be.repository.ParishionerProfileRepository;
import com.mq76.holyTask_be.repository.SubParishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class SubParishServiceImpl implements SubParishService {
    private final SubParishRepository subParishRepository;
    private final ParishRepository parishRepository;
    @Override
    public ResponseObject createOrUpdateSubParish(SubParish subParish) {
        try {
            subParish.setCreatedAt(new Date());
            if(subParish.getId() == -1){
                subParish.setId(null);
            }
            Parish parish = parishRepository.findById(subParish.getParish().getId()).orElse(null);
            subParish.setParish(parish);
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

    @Override
    public ResponseObject updateSubParish(SubParish subParish) {
        try{
            if(subParish.getId()!=null){
                SubParish oldSubParish = this.subParishRepository.findById(subParish.getId()).orElse(null);
                oldSubParish.setUpdatedAt(new Date());
                oldSubParish.setName(subParish.getName());
                oldSubParish.setParish(subParish.getParish());
                subParishRepository.save(oldSubParish);

                return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,subParish);
            }
            else {
                return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
            }
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject deleteSubParish(Integer subParishId) {
        try{
            SubParish subParish = this.subParishRepository.findById(subParishId).orElse(null);
            if(subParish!=null){
                subParishRepository.delete(subParish);
                return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,subParish);
            }else {
                return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
            }
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.SUB_PARISH_USED, null);
        }
    }


}
