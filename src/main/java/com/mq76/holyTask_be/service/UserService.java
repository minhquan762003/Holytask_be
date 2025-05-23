package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.Role;
import com.mq76.holyTask_be.model.User;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface UserService {
    ResponseObject registerUser(User user);
    ResponseObject loginUser(String username, String password);
    ResponseObject getUserByUsername(String username);
    ResponseObject getUserById(Integer id);
    ResponseObject getUsersByRole(Role role);
    ResponseObject deactivateUser(Integer id);
}

