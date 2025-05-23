package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {
    List<Note> findByUser_Id(Integer userId);
}
