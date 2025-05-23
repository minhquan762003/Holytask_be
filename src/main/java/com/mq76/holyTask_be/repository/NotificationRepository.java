package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser_Id(Integer userId);
    List<Notification> findByUser_IdAndIsReadFalse(Integer userId);
}

