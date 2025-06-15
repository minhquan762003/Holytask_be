package com.mq76.holyTask_be.model;

import lombok.Data;

import java.util.Date;
@Data
public class EditProfile {
    private String avatar;
    private String fullName;
    private Date ordinationDate;
    private String bio;
    private String email;
    private String phone;
    private String address;
    private Integer parishId;
}
