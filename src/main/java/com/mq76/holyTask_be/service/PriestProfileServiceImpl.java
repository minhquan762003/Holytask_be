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
        try {
            Optional<User> optionalUser = userRepository.findById(userId);
            if (!optionalUser.isPresent()) {
                return new ResponseObject(MessageConstants.FAILED, MessageConstants.NOT_FOUND_USER, null);
            }

            User user = optionalUser.get();
            user.setUpdatedAt(new Date());
            user.setUpdatedUser(user.getUsername().trim());
            user.setProfilePictureUrl(editProfile.getAvatar().trim());
            user.setEmail(editProfile.getEmail().trim());
            user.setPhoneNumber(editProfile.getPhone().trim());
            userRepository.save(user);

            Optional<PriestProfile> optionalPriestProfile = priestProfileRepository.findByUser_Id(userId);
            PriestProfile priestProfile;

            if (optionalPriestProfile.isPresent()) {
                priestProfile = optionalPriestProfile.get();
                priestProfile.setUpdatedAt(new Date());
                priestProfile.setUpdatedUser(user.getUsername());
            } else {
                priestProfile = new PriestProfile();
                priestProfile.setCreatedAt(new Date());
                priestProfile.setCreatedUser(user.getUsername());
                priestProfile.setUser(user);
            }

            // Lưu thông tin cơ bản
            priestProfile.setFullName(editProfile.getFullName());
            priestProfile.setBio(editProfile.getBio());
            priestProfile.setOrdinationDate(editProfile.getOrdinationDate());

            // Xử lý giáo xứ (nếu có thay đổi)
            Parish oldParish = priestProfile.getParish();
            Parish newParish = parishRepository.findById(editProfile.getParishId()).orElse(null);

            if (oldParish != null && newParish != null && !oldParish.getId().equals(newParish.getId())) {
                oldParish.setIsSelected(0);
                parishRepository.save(oldParish);

                newParish.setIsSelected(1);
                parishRepository.save(newParish);
            }

            if (newParish != null) {
                priestProfile.setParish(newParish);
            }

            priestProfileRepository.save(priestProfile);

            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, editProfile);

        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
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
