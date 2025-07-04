package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.Note;
import com.mq76.holyTask_be.model.ResponseObject;


public interface NoteService {
    ResponseObject createOrUpdateNote(Note note);
    ResponseObject findByUser_Id(Integer userId);
}
