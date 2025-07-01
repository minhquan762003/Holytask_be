package com.mq76.holyTask_be.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ParishionerRequest {
    Integer id;
    String fullName;
    String phoneNumber;
    Date birthDate;
    String address;
    String imgUrl;
    Integer parishGroupId;
    Integer subParishId;
    String createdUser;
}
