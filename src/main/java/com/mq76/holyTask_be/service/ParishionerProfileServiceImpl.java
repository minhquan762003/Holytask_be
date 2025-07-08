package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.*;
import com.mq76.holyTask_be.repository.ParishGroupRepository;
import com.mq76.holyTask_be.repository.ParishionerProfileRepository;
import com.mq76.holyTask_be.repository.SubParishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ParishionerProfileServiceImpl implements ParishionerProfileService {
    @Autowired
    private final ParishionerProfileRepository repository;

    @Autowired
    private final ParishGroupRepository parishGroupRepository;

    @Autowired
    private final SubParishRepository subParishRepository;
    @Override
    public ResponseObject createParishioner(ParishionerRequest parishionerRequest) {
        try {

            ParishionerProfile profile = new ParishionerProfile();

            profile.setPhoneNumber(parishionerRequest.getPhoneNumber().trim());
            profile.setFullName(parishionerRequest.getFullName().trim());
            profile.setAddress(parishionerRequest.getAddress().trim());
            profile.setDateOfBirth(parishionerRequest.getBirthDate());
            ParishGroup parishGroup = parishGroupRepository.getById(parishionerRequest.getParishGroupId());
            SubParish subParish = subParishRepository.getById(parishionerRequest.getSubParishId());
            profile.setParishGroup(parishGroup);
            profile.setSubParish(subParish);
            if( !parishionerRequest.getImgUrl().isEmpty()){
                profile.setImgUrl(parishionerRequest.getImgUrl());
            }else {
                profile.setImgUrl(MessageConstants.IMG_URL_DEFAULT);
            }
            profile.setCreatedUser(parishionerRequest.getCreatedUser());
            profile.setCreatedAt(new Date());
            if(repository.findByPhoneNumber(parishionerRequest.getPhoneNumber()).isPresent()) {
                return new ResponseObject(MessageConstants.FAILED, MessageConstants.NUMBER_EXISTED,null);
            }else {
                repository.save(profile);
                return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, profile);
            }
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject updateParishioner( ParishionerRequest parishionerRequest ) {
        // Kiểm tra ID hợp lệ và tồn tại
        Optional<ParishionerProfile> existingOpt = repository.findById(parishionerRequest.getId());

        if (existingOpt.isPresent()) {
            ParishionerProfile existingProfile = existingOpt.get();

            // Cập nhật thông tin
            if(repository.findByPhoneNumber(parishionerRequest.getPhoneNumber()).isPresent() && !existingProfile.getPhoneNumber().equals(parishionerRequest.getPhoneNumber())) {
                return new ResponseObject(MessageConstants.FAILED, MessageConstants.NUMBER_EXISTED,null);
            }
            existingProfile.setPhoneNumber(parishionerRequest.getPhoneNumber());
            existingProfile.setFullName(parishionerRequest.getFullName());
            existingProfile.setDateOfBirth(parishionerRequest.getBirthDate());
            existingProfile.setAddress(parishionerRequest.getAddress());

            if (parishionerRequest.getSubParishId() != null) {
                SubParish subParish = subParishRepository.getById(parishionerRequest.getSubParishId());
                existingProfile.setSubParish(subParish);
            }

            if (parishionerRequest.getParishGroupId() != null ) {
                ParishGroup parishGroup = parishGroupRepository.getById(parishionerRequest.getParishGroupId());
                existingProfile.setParishGroup(parishGroup);
            }

            existingProfile.setImgUrl(parishionerRequest.getImgUrl());
            existingProfile.setUpdatedAt(new Date());

            ParishionerProfile updated = repository.save(existingProfile);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, updated);
        }

        return new ResponseObject(MessageConstants.FAILED, MessageConstants.PARISHIONER_NOT_FOUND, null);
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

    @Override
    public ResponseObject getByParishId(Integer parishId) {
        try{
            List<ParishionerProfile> lst = repository.findByParishId(parishId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, lst);
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject findParishioners(Integer parishId, Integer groupId, String name) {
        try {
            List<ParishionerProfile> lst;

            boolean hasGroup = groupId != -1;
            boolean hasName = !Objects.equals(name, "null");

            if (hasGroup && hasName) {
                lst = repository.searchParishionersParishIdGroupIdName(parishId, groupId, name);
            } else if (hasGroup) {
                lst = repository.findByParishIdAndGroupId(parishId, groupId);
            } else if (hasName) {
                lst = repository.findByNameAndParishId(parishId, name);
            } else {
                lst = repository.findByParishId(parishId);
            }

            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, lst);
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject deleteParishioner(Integer parishId) {
        try{
            repository.deleteById(parishId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, null);
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getAllParishionersOrderByViewDate(Integer parishId) {
        try{
            List<ParishionerProfile> lst = repository.findByParishIdOrderByViewDateDesc(parishId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, lst);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject setParishionerViewDate(Integer parishionerId) {
        try{
            ParishionerProfile found = repository.findById(parishionerId).get();
            found.setViewDate(new Date());
            repository.save(found);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, found);
        }catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

}
