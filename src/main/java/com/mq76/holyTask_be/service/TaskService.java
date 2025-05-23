package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.Task;

import java.util.List;

public interface TaskService {
    ResponseObject createTask(Task task);
    ResponseObject getTasksByUserId(Integer userId);
    ResponseObject getPendingTasks(Integer userId);
    ResponseObject markTaskAsDone(Integer taskId);
}
