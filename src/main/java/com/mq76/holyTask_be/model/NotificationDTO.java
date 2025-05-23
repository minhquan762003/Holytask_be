package com.mq76.holyTask_be.model;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

@Data
public class NotificationDTO {
    private String headline;
    private String message;
    private Integer scheduleId;

    public NotificationDTO(String headline, String message, Integer scheduleId) {
        this.headline = headline;
        this.message = message;
        this.scheduleId = scheduleId;
    }

}
