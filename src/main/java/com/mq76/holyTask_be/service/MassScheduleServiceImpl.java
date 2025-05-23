package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.MassSchedule;
import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.MassScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MassScheduleServiceImpl implements MassScheduleService {

    private final MassScheduleRepository repository;

    @Override
    public ResponseObject createOrUpdateMass(MassSchedule mass) {
        try{
            repository.save(mass);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, mass);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }

    }

    @Override
    public ResponseObject getByPriestId(Integer priestId) {
        try {
            repository.findByPriest_Id(priestId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findByPriest_Id(priestId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }



    @Override
    public ResponseObject findByDatetimeBetween(LocalDateTime from, LocalDateTime to) {
        try {
            repository.findByDatetimeBetween(from,to);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, repository.findByDatetimeBetween(from, to));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, null);
        }
    }
}
