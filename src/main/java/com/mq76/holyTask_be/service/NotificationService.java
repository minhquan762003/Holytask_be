package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.Notification;
import com.mq76.holyTask_be.model.ResponseObject;

import java.util.List;

public interface NotificationService {
    ResponseObject sendNotification(Notification notification);
    ResponseObject getNotificationsByUserId(Integer userId);
}
