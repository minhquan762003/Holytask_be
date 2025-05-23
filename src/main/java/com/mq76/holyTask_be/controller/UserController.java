package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.auth.JwtUtils;
import com.mq76.holyTask_be.model.LoginRequest;
import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.User;
import com.mq76.holyTask_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody User user) {
        ResponseObject responseObject = userService.registerUser(user);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> loginUser(@RequestBody LoginRequest loginRequest) {
        ResponseObject responseObject = userService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
        if(responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseObject);
        }
    }

}
