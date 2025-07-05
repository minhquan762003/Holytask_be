package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.auth.JwtUtils;
import com.mq76.holyTask_be.model.*;
import com.mq76.holyTask_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/userId/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getUserById(@PathVariable("userId") Integer userId) {
        ResponseObject responseObject = userService.getUserById(userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> getAllUsers() {
        ResponseObject responseObject = userService.getAllUsers();
        if(responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @PutMapping("/updateUsers")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseObject> updateUsers(@RequestBody User user, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        ResponseObject responseObject = userService.updateUser(user, userPrincipal);
        if(responseObject.getStatus().equals(MessageConstants.OK)) {
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

}
