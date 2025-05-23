package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Notification;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository repository;

    @Override
    public ResponseObject sendNotification(Notification notification) {
        try {
            repository.save(notification);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.save(notification));
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject getNotificationsByUserId(Integer userId) {
        try{
            repository.findByUser_Id(userId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findAll());
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }
}
