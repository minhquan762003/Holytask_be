package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.EditProfile;
import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.PriestProfile;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.PriestProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/priestProfile")
@RequiredArgsConstructor
public class PriestProfileController {
    private final PriestProfileService priestProfileService;
    @PostMapping("/addOrUpdate")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> createOrUpdateProfile(@RequestBody PriestProfile priestProfile) {
        ResponseObject responseObject = priestProfileService.createProfile(priestProfile);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getByUserId(@PathVariable Integer userId) {
        ResponseObject responseObject = priestProfileService.getByUserId(userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/parishId/{parishId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getByParishId(@PathVariable Integer parishId) {
        ResponseObject responseObject = priestProfileService.getByParishId(parishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @PostMapping("/update/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> updateProfile(@RequestBody EditProfile editProfile, @PathVariable Integer userId) {
        ResponseObject responseObject = priestProfileService.updateProfile(editProfile, userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }

    }
}
