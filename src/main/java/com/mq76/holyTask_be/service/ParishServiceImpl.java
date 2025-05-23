package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.ParishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class ParishServiceImpl implements ParishService {

    private final ParishRepository repository;
    @Override
    public ResponseObject createOrUpdateParish(Parish parish) {
        try {
            repository.save(parish);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG, parish);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject findByNameContainingIgnoreCase(String name) {
        try {
            repository.findByNameContainingIgnoreCase(name);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,repository.findByNameContainingIgnoreCase(name));
        }catch (Exception e){

            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }
}
