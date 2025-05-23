package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Report;
import com.mq76.holyTask_be.model.ReportType;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService reportService;
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createOrUpdateReport(@RequestBody Report report) {
        ResponseObject responseObject = reportService.createOrUpdateReport(report);
        if (responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
    @GetMapping("/{priestId}")
    public ResponseEntity<ResponseObject> findByPriest_Id(@PathVariable Integer priestId) {
        ResponseObject responseObject = reportService.findByPriest_Id(priestId);
        if (responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/fingByType")
    public ResponseEntity<ResponseObject> findByType(@RequestParam ReportType type) {
        ResponseObject responseObject = reportService.findByType(type);
        if (responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
}
