package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.Task;
import com.mq76.holyTask_be.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> addTask(@RequestBody Task task) {
        ResponseObject responseObject = taskService.createTask(task);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getTasksByUserId(Integer userId) {
        ResponseObject responseObject = taskService.getTasksByUserId(userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseObject> markTaskAsDone(Integer taskId) {
        ResponseObject responseObject = taskService.markTaskAsDone(taskId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

}
