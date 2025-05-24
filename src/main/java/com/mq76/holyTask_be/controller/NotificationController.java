package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Notification;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.SendEmailRequest;
import com.mq76.holyTask_be.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addNotification(@RequestBody Notification notification) {
        ResponseObject responseObject = notificationService.sendNotification(notification);

        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/{userId}")

    public ResponseEntity<ResponseObject> getNotificationsByUserId(@PathVariable Integer userId) {
        ResponseObject responseObject = notificationService.getNotificationsByUserId(userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    private Set<Integer> notified = ConcurrentHashMap.newKeySet();

    @PostMapping("/ack")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> acknowledge(@RequestBody Map<String, Integer> body) {
        Integer scheduleId = body.get("scheduleId");

        if (scheduleId != null) {
            ResponseObject responseObject = new ResponseObject();
            responseObject.setStatus(MessageConstants.OK);
            responseObject.setMessage("Đã nhận được scheduleId: " + scheduleId);
            notified.add(scheduleId);
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }



    public boolean isNotified(int id) {
        return notified.contains(id);
    }
}
