package com.mq76.holyTask_be.model;

import lombok.Data;

@Data
public class SendEmailRequest {
    private String email;
    private Integer idVisit;
}
