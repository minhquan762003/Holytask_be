package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.ParishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/Parish")
@RestController
@RequiredArgsConstructor
public class ParishController {

    private final ParishService parishService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> createOrUpdateParish(@RequestBody Parish parish) {
        ResponseObject responseObject = parishService.createOrUpdateParish(parish);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/find")
    public ResponseEntity<ResponseObject> findByNameContainingIgnoreCase(@RequestParam String name) {
        ResponseObject responseObject = parishService.findByNameContainingIgnoreCase(name);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
}
