package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.PriestProfile;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.VisitSchedule;
import com.mq76.holyTask_be.repository.PriestProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PriestProfileServiceImpl implements PriestProfileService {

    private final PriestProfileRepository priestRepo;

    @Override
    public ResponseObject createProfile(PriestProfile profile) {
        try {

            if(priestRepo.findByUser_Id(profile.getUser().getId()).isPresent()) {
                profile.setUpdatedAt(new Date());
                priestRepo.save(profile);
            }else {
                profile.setCreatedAt(new Date());
                priestRepo.save(profile);
            }
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, profile);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getByUserId(Integer userId) {
        try{
            priestRepo.findByUser_Id(userId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, priestRepo.findByUser_Id(userId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getByParishId(Integer parishId) {
        try{
            priestRepo.findByParish_Id(parishId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, priestRepo.findByParish_Id(parishId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject updateProfile(PriestProfile profile, Integer idProfile) {
        if (priestRepo.existsById(idProfile)) {
            PriestProfile updated = priestRepo.findById(idProfile).map(
                    profile1 -> {
                        profile1.setFullName(profile.getFullName());
                        profile1.setOrdinationDate(profile.getOrdinationDate());
                        profile1.setUpdatedUser(profile.getUpdatedUser());
                        profile1.setUpdatedAt(profile.getUpdatedAt());
                        return priestRepo.save(profile1);
                    }).orElseGet(() -> {

                return null;
            });
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, updated);
        } else {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }
}
