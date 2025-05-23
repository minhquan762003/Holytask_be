package com.mq76.holyTask_be.service;


import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.Report;
import com.mq76.holyTask_be.model.ReportType;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    @Override
    public ResponseObject createOrUpdateReport(Report report) {
        try {
            reportRepository.save(report);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, reportRepository.save(report));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI, null);
        }
    }

    @Override
    public ResponseObject findByPriest_Id(Integer priestId) {
        try {
            reportRepository.findByPriest_Id(priestId);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG,reportRepository.findByPriest_Id(priestId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }
    }

    @Override
    public ResponseObject findByType(ReportType type) {
        try {
            reportRepository.findByType(type);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG,reportRepository.findByType(type));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED,MessageConstants.THAT_BAI, null);
        }
    }
}
