package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ParishionerProfile;
import com.mq76.holyTask_be.model.ParishionerRequest;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.service.ParishionerProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/parishionerProfile")
@RequiredArgsConstructor
public class ParishionerProfileController {

    private final ParishionerProfileService parishionerProfileService;

    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> createOrUpdateProfile(@RequestBody ParishionerRequest parishionerRequest){
        ResponseObject responseObject = parishionerProfileService.createParishioner(parishionerRequest);

        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> updateProfile(@RequestBody ParishionerRequest parishionerRequest){
        ResponseObject responseObject = parishionerProfileService.updateParishioner(parishionerRequest);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

//    @GetMapping("/{userId}")
//    public ResponseEntity<ResponseObject> getByUserId(@PathVariable Integer userId){
//        ResponseObject responseObject = parishionerProfileService.getByUserId(userId);
//        if(responseObject.getStatus().equals(MessageConstants.OK)){
//            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
//        }else {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
//        }
//    }
    @GetMapping("/subParish/{subParishId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getBySubParishId(@PathVariable Integer subParishId){
        ResponseObject responseObject = parishionerProfileService.getBySubParishId(subParishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/parish/{parishId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getByParishId(@PathVariable Integer parishId){
        ResponseObject responseObject = parishionerProfileService.getByParishId(parishId);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

    @GetMapping("/parish/group/{parishId}/{groupId}/{name}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> getByParishIdAndGroupId(
            @PathVariable Integer parishId,
            @PathVariable Integer groupId,  @PathVariable String name) {

        ResponseObject responseObject = parishionerProfileService.findParishioners(parishId, groupId, name);

        HttpStatus status = responseObject.getStatus().equals(MessageConstants.OK)
                ? HttpStatus.OK
                : HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(responseObject);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> deleteParishioner(@PathVariable Integer id){
        ResponseObject responseObject = parishionerProfileService.deleteParishioner(id);
        if(responseObject.getStatus().equals(MessageConstants.OK)){
            return ResponseEntity.status(HttpStatus.OK).body(responseObject);
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseObject);
        }
    }

}
