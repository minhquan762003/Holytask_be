package com.mq76.holyTask_be.controller;


import com.mq76.holyTask_be.model.MassSchedule;
import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.MassScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/mass")
@RequiredArgsConstructor
public class MassScheduleController {
    private final MassScheduleService massScheduleService;

    @PostMapping
    public ResponseEntity<ResponseObject> createOrUpdateMass(@RequestBody MassSchedule mass) {
        ResponseObject responseObject = massScheduleService.createOrUpdateMass(mass);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/priest/{priestId}")
    public ResponseEntity<ResponseObject> getByPriestId(@PathVariable Integer priestId) {
        ResponseObject responseObject = massScheduleService.getByPriestId(priestId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
    @GetMapping("/range")
    public ResponseEntity<ResponseObject> getMassInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to
    ) {
        ResponseObject responseObject = massScheduleService.findByDatetimeBetween(from, to);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }

}
