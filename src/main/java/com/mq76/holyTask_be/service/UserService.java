package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.*;

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
    ResponseObject findUserById(Integer id);
    ResponseObject getAllUsers();
    ResponseObject updateUser(User user, UserPrincipal userPrincipal);
    ResponseObject resetPassword(Integer id, ResetPassRequest resetPassRequest);
}

