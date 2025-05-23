package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Note;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/note")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @PostMapping("/add")
    public ResponseEntity<ResponseObject> createOrUpdateNote(@RequestBody Note note) {
        ResponseObject responseObject = noteService.createOrUpdateNote(note);

        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseObject> getNoteByUserId(@PathVariable Integer userId) {
        ResponseObject responseObject = noteService.findByUser_Id(userId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }




}
