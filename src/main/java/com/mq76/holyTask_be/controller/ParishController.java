package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Parish;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.model.UserPrincipal;
import com.mq76.holyTask_be.service.ParishService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/Parish")
@RestController
@RequiredArgsConstructor
public class ParishController {

    private final ParishService parishService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> createParish(@RequestBody Parish parish, @AuthenticationPrincipal UserPrincipal user) {
        ResponseObject responseObject = parishService.createParish(parish, user);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> updateParish(@RequestBody Parish parish, @AuthenticationPrincipal UserPrincipal user) {
        ResponseObject responseObject = parishService.updateParish(parish, user);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }

    }

    @DeleteMapping("/delete/{parishId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> deleteParish(@PathVariable Integer parishId){
        ResponseObject responseObject = parishService.deleteParish(parishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }



    @GetMapping("/find")
    public ResponseEntity<ResponseObject> findByNameContainingIgnoreCase(@RequestParam String name) {
        ResponseObject responseObject = parishService.findByNameContainingIgnoreCase(name);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/parishId/{parishId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> findByParishId(@PathVariable Integer parishId) {
        ResponseObject responseObject = parishService.finbyParishId(parishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/getAllParish")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getAllParish() {
        ResponseObject responseObject = parishService.getAllParish();
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/getParishIsSelected")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getParishIsSelected() {
        ResponseObject responseObject = parishService.findbyIsSelected();
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }
}
