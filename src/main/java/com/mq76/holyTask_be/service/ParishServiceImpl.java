package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.UserPrincipal;
import com.mq76.holyTask_be.repository.ParishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParishServiceImpl implements ParishService {

    private final ParishRepository repository;
    @Override
    public ResponseObject createParish(Parish parish,  UserPrincipal user) {
        try {
            parish.setCreatedAt(new Date());
            parish.setCreatedUser(user.getUsername());
            parish.setIsSelected(0);
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

    @Override
    public ResponseObject finbyParishId(Integer parishId) {
        try{
            Optional<Parish> parish = repository.finbyParishId(parishId);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,parish);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getAllParish() {
        try{
            List<Parish> parishList = repository.findAll();
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,parishList);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.OK,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject updateParish(Parish parish,UserPrincipal user) {
        try{

                Parish foundParish = repository.findById(parish.getId()).get();
                foundParish.setName(parish.getName());
                foundParish.setLocation(parish.getLocation());
                foundParish.setUpdatedUser(user.getUsername());
                foundParish.setUpdatedAt(new Date());
                repository.save(foundParish);

                return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,foundParish);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject findbyIsSelected() {
        try{
            List<Parish> parishList = repository.findbyIsSelected();
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,parishList);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject deleteParish(Integer parishId) {
        try{
            Parish parish = repository.findById(parishId).get();
            repository.delete(parish);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,parish);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.PARISH_USED,null);
        }
    }


}
