package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.*;
import com.mq76.holyTask_be.repository.ParishRepository;
import com.mq76.holyTask_be.repository.PriestProfileRepository;
import com.mq76.holyTask_be.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PriestProfileRepository priestProfileRepository;
    @Autowired
    private ParishRepository parishRepository;

    @Override
    public ResponseObject updateProfile(EditProfile editProfile, Integer userId) {
        User foundUser = userRepository.findById(userId).get();
        try{
            if(userRepository.findById(userId).isPresent()) {
                User user = userRepository.findById(userId).map(
                        user1 -> {
                            user1.setUpdatedAt(new Date());
                            user1.setUpdatedUser(foundUser.getUsername().trim());
                            user1.setProfilePictureUrl(editProfile.getAvatar().trim());
                            user1.setEmail(editProfile.getEmail().trim());
                            user1.setPhoneNumber(editProfile.getPhone().trim());
                            return userRepository.save(user1);
                        }).orElseGet(() ->{
                    return null;
                });

            }

//            PriestProfile foundPriestProfile = priestRepo.findByUser_Id(userId).get();
            Optional<PriestProfile> optionalPriestProfile = priestProfileRepository.findByUser_Id(userId);
            PriestProfile priestProfile;

            if (optionalPriestProfile.isPresent()) {
                // Cập nhật bản ghi hiện có
                priestProfile = optionalPriestProfile.get();
                priestProfile.setUpdatedAt(new Date());
                priestProfile.setUpdatedUser(foundUser.getUsername());
            } else {
                // Tạo bản ghi mới
                priestProfile = new PriestProfile();
                priestProfile.setCreatedAt(new Date());
                priestProfile.setUpdatedUser(foundUser.getUsername());
                priestProfile.setUser(foundUser);
            }

// Dữ liệu dùng chung cho cả cập nhật hoặc tạo mới
            priestProfile.setFullName(editProfile.getFullName());
            priestProfile.setBio(editProfile.getBio());
            priestProfile.setOrdinationDate(editProfile.getOrdinationDate());
            priestProfile.setParish(parishRepository.findById(editProfile.getParishId()).orElse(null));

            priestProfileRepository.save(priestProfile);

//            PriestProfile priestProfile = priestRepo.findByUser_Id(userId).get();
//            Parish parish = priestProfile.getParish();
//            if(parish != null) {
//                Parish parish1 = parishRepository.findById(parish.getId()).map(
//                        parish2 -> {
//                            parish2.setUpdatedAt(new Date());
//                            parish2.setUpdatedUser(foundUser.getUsername());
//                            parish2.setName(editProfile.getParish());
//                            return parishRepository.save(parish2);
//                        }).orElseGet(()->{
//                            return null;
//                });
//            }

            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, editProfile);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI,null);
        }
    }



//    @Override
//    public ResponseObject updateProfile(PriestProfile profile, Integer idProfile) {
//        if (priestRepo.existsById(idProfile)) {
//            PriestProfile updated = priestRepo.findById(idProfile).map(
//                    profile1 -> {
//                        profile1.setFullName(profile.getFullName());
//                        profile1.setOrdinationDate(profile.getOrdinationDate());
//                        profile1.setUpdatedUser(profile.getUpdatedUser());
//                        profile1.setUpdatedAt(profile.getUpdatedAt());
//                        return priestRepo.save(profile1);
//                    }).orElseGet(() -> {
//
//                return null;
//            });
//            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, updated);
//        } else {
//            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
//        }
//    }
}
