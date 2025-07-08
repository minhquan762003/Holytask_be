package com.mq76.holyTask_be.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResetPassRequest {
    String newPassword;
    String oldPassword;
}
