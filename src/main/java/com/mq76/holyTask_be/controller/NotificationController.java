package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Notification;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
