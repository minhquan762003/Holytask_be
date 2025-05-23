package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByUser_Id(Integer userId);
    List<Task> findByUser_IdAndIsDoneFalse(Integer userId);
}

