package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.SendEmailRequest;
import com.mq76.holyTask_be.model.VisitSchedule;
import com.mq76.holyTask_be.service.VisitScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;

@RestController
@RequestMapping("/api/visitSchedule")
@RequiredArgsConstructor
public class VisitScheduleController {
    private final VisitScheduleService visitScheduleService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> createScheduleVisit(@RequestBody VisitSchedule visitSchedule ) {
        ResponseObject responseObject = visitScheduleService.createScheduleVisit(visitSchedule);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/{priestId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getVisitsByPriestId(@PathVariable Integer priestId) {
        ResponseObject responseObject = visitScheduleService.getVisitsByPriestId(priestId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
    @GetMapping("/range")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getMassInRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date to
    ) {
        ResponseObject responseObject = visitScheduleService.findByDatetimeBetween(from, to);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }


    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject>updateScheduleById(@RequestBody VisitSchedule visitSchedule, @PathVariable Integer id){
        ResponseObject responseObject = visitScheduleService.updateScheduleVisit(visitSchedule, id);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }

    @GetMapping("/idVisit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject>getByIdVisit(@PathVariable Integer id){
        ResponseObject responseObject = visitScheduleService.findById(id);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }



    @PostMapping("/sendEmail")
    @PreAuthorize("hasAnyAuthority('ADMIN','PRIEST')")
    public ResponseEntity<ResponseObject> sendEmailForVisit(@RequestBody SendEmailRequest sendEmailRequest) {
        ResponseObject responseObject = visitScheduleService.sendEmailNotification(sendEmailRequest.getIdVisit(), sendEmailRequest.getEmail());
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PRIEST')")
    public ResponseEntity<ResponseObject> deleteScheduleById(@PathVariable Integer id){
        ResponseObject responseObject = visitScheduleService.deleteScheduleVisit(id);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/byDate/priestId/{priestId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PRIEST')")
    public ResponseEntity<ResponseObject> findVisitByDate(@RequestParam String strDate, @PathVariable Integer priestId){
        ResponseObject responseObject = visitScheduleService.findVisitByPriestIdAndDate(strDate,priestId);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/scheduleId/{scheduleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PRIEST')")
    public ResponseEntity<ResponseObject> getScheduleById(@PathVariable Integer scheduleId){
        ResponseObject responseObject = visitScheduleService.getVisitByScheduleId(scheduleId);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @PutMapping("/setStatus/{scheduleId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','PRIEST')")
    public ResponseEntity<ResponseObject> setStatusByScheduleId(@PathVariable Integer scheduleId){
        ResponseObject responseObject = visitScheduleService.setStatusByScheduleId(scheduleId);
        if (responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

}
