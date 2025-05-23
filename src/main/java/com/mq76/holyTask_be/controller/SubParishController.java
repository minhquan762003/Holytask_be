package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.SubParish;
import com.mq76.holyTask_be.service.SubParishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subparish")
@RequiredArgsConstructor
public class SubParishController {
    private final SubParishService subParishService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createOrUpdateSubParish(SubParish subParish) {
        ResponseObject responseObject=subParishService.createOrUpdateSubParish(subParish);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/{subParishId}")
    public ResponseEntity<ResponseObject> findByParish_Id(@PathVariable Integer subParishId) {
        ResponseObject responseObject=subParishService.findByParish_Id(subParishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
}
