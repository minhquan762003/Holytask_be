package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.MessageConstants;
import com.mq76.holyTask_be.model.ResponseObject;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.InputStream;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/minio")
public class MinioController {

    @Autowired
    private MinioClient minioClient;

    private static final String BUCKET_NAME = "document";

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> upload(@RequestParam("file") MultipartFile file) {
        try {
            String originalFileName = file.getOriginalFilename();
            String fileName = UUID.randomUUID() + "_" + originalFileName;

            // Upload to MinIO
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );

            // Tạo link download
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/minio/download/")
                    .path(fileName)
                    .toUriString();

            // Trả về chi tiết
            Map<String, String> data = new HashMap<>();
            data.put("fileName", fileName);
            data.put("originalFileName", originalFileName);
            data.put("downloadUrl", downloadUrl);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, data));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,
                            "❌ Upload failed: " + e.getMessage()));
        }
    }

    @GetMapping("/download/{fileName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<byte[]> download(@PathVariable String fileName) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .build()
            );

            byte[] data = stream.readAllBytes();
            stream.close();

            // Đoán content type từ tên file
            String contentType = URLConnection.guessContentTypeFromName(fileName);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header("Content-Disposition", "attachment; filename=\"" + fileName + "\"")
                    .body(data);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}


