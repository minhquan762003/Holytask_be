package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.Task;
import com.mq76.holyTask_be.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepo;

    @Override
    public ResponseObject createTask(Task task) {
        try {
            taskRepo.save(task);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG, task);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject getTasksByUserId(Integer userId) {
        try {
            taskRepo.findByUser_Id(userId);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THANH_CONG,taskRepo.findAll());
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject getPendingTasks(Integer userId) {
        try {
            taskRepo.findByUser_IdAndIsDoneFalse(userId);
            return new ResponseObject(MessageConstants.OK,MessageConstants.THAT_BAI,taskRepo.findByUser_IdAndIsDoneFalse(userId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject markTaskAsDone(Integer taskId) {
        try{
            taskRepo.findById(taskId).ifPresent(task -> {
                task.setIsDone(true);
                taskRepo.save(task);
            });
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, null);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }
}
