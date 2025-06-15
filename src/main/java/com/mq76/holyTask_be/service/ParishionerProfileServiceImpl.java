package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ParishionerProfile;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.ParishionerProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParishionerProfileServiceImpl implements ParishionerProfileService {

    private final ParishionerProfileRepository repository;

    @Override
    public ResponseObject createParishioner(ParishionerProfile profile) {
        try {
            profile.setPhoneNumber(profile.getPhoneNumber().trim());
            profile.setFullName(profile.getFullName().trim());
            profile.setAddress(profile.getAddress().trim());
            repository.save(profile);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, profile);
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject updateParishioner(ParishionerProfile profile) {
        Optional<ParishionerProfile> profileOptional = repository.findByPhoneNumber(profile.getPhoneNumber());

        if(profileOptional.isPresent()) {
            ParishionerProfile profileToUpdate = profileOptional.map(parishioner ->{
                parishioner.setPhoneNumber(profile.getPhoneNumber());
                parishioner.setFullName(profile.getFullName());
                parishioner.setDateOfBirth(profile.getDateOfBirth());
                parishioner.setAddress(profile.getAddress());
                parishioner.setSubParish(profile.getSubParish());
                parishioner.setUpdatedAt(new Date());
                return repository.save(parishioner);
            }).orElseGet(() -> {
                return  null;
            });
        }

        return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG,profile);
    }

//    @Override
//    public ResponseObject getByUserId(Integer userId) {
//        try{
//            repository.findByUser_Id(userId);
//            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findByUser_Id(userId));
//        }catch (Exception e) {
//            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
//        }
//    }

    @Override
    public ResponseObject getBySubParishId(Integer parishId) {
        try{
            repository.findBySubParish_Id(parishId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findBySubParish_Id(parishId));
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }
}
