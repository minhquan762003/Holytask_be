package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ParishionerProfile;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.ParishionerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parishionerProfile")
@RequiredArgsConstructor
public class ParishionerProfileController {

    private final ParishionerProfileService parishionerProfileService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createOrUpdateProfile(@RequestBody ParishionerProfile parishionerProfile){
        ResponseObject responseObject = parishionerProfileService.createOrUpdateProfile(parishionerProfile);

        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getByUserId(@PathVariable Integer userId){
        ResponseObject responseObject = parishionerProfileService.getByUserId(userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
    @GetMapping("/{subParishId}")
    public ResponseEntity<ResponseObject> getBySubParishId(@PathVariable Integer subParishId){
        ResponseObject responseObject = parishionerProfileService.getBySubParishId(subParishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
}
