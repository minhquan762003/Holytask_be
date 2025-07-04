package com.mq76.holyTask_be.service;

import com.mq76.holyTask_be.model.Documents;
import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import com.mq76.holyTask_be.repository.DocumentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentsServiceImpl implements DocumentsService{

    @Autowired
    private DocumentsRepository documentsRepository;

    @Override
    public ResponseObject uploadDocuments(Documents documents) {
        return null;
    }

    @Override
    public ResponseObject deleteDocuments(Long documentId) {
        Documents foundDoc = documentsRepository.getById(documentId);

        try {
            foundDoc.setDeleted(1);
            documentsRepository.save(foundDoc);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG,documentsRepository.getById(documentId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);
        }

    }

    @Override
    public ResponseObject downLoadDocuments(Integer documentId) {
        return null;
    }

    @Override
    public ResponseObject getDocumentByPriestId(Integer priestId) {
        try{
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, documentsRepository.findByPriestId(priestId));
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI, documentsRepository.findByPriestId(priestId));
        }
    }

    @Override
    public ResponseObject findByName(String name) {
        try{
            List<Documents> lstDoc = documentsRepository.findByOriginalFileName(name);
            return new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, lstDoc);
        }catch (Exception e){
            return new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,null);

        }
    }
}
