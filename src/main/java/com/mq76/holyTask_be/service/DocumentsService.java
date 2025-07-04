package com.mq76.holyTask_be.service;


import com.mq76.holyTask_be.model.Documents;
import com.mq76.holyTask_be.model.ResponseObject;
import org.apache.coyote.Response;
import org.springframework.stereotype.Service;

@Service
public interface DocumentsService {
    ResponseObject uploadDocuments(Documents documents);
    ResponseObject deleteDocuments(Long documentId);
    ResponseObject downLoadDocuments(Integer documentId);
    ResponseObject getDocumentByPriestId(Integer priestId);
    ResponseObject findByName(String name);
}
