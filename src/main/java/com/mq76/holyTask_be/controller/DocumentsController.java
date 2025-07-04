package com.mq76.holyTask_be.controller;


import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.User;
import com.mq76.holyTask_be.service.DocumentsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentsController {
    @Autowired
    private final DocumentsService documentsService;

    @GetMapping("/priestId/{priestId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getDocByPriestId(@PathVariable Integer priestId) {
        ResponseObject responseObject = documentsService.getDocumentByPriestId(priestId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }


    @PutMapping("/deleteDoc/{docId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> deleteDocById(@PathVariable Long docId) {
        ResponseObject responseObject = documentsService.deleteDocuments(docId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/findByName/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getDocumentsByName(@PathVariable String name) {
        ResponseObject responseObject = documentsService.findByName(name);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }


}
