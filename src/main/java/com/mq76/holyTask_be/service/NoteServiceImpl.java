package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Note;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteServiceImpl implements NoteService {

    private final NoteRepository noteRepository;

    @Override
    public ResponseObject createOrUpdateNote(Note note) {
        try {
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, noteRepository.save(note));
        } catch (Exception e) {
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, "");
        }
    }

    @Override
    public ResponseObject findByUser_Id(Integer userId) {
        try {
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, noteRepository.findByUser_Id(userId));
        } catch (Exception e) {
//            throw new RuntimeException(e);
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, "");
        }
    }
}
