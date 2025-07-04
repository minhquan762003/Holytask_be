package com.mq76.holyTask_be.controller;

import com.mq76.holyTask_be.model.*;
import com.mq76.holyTask_be.repository.DocumentsRepository;
import com.mq76.holyTask_be.repository.PriestProfileRepository;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @Autowired
    private DocumentsRepository documentsRepository;

    @Autowired
    private PriestProfileRepository priestProfileRepository;

    @PostMapping("/upload")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<ResponseObject> upload(@RequestParam("file") MultipartFile file,
                                                 @RequestParam("fileType") Integer fileType,
                                                 @RequestParam("description") String description,
                                                 @AuthenticationPrincipal UserPrincipal user) {
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

            // Tạo link tải
            String downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/minio/download/")
                    .path(fileName)
                    .toUriString();

            // 👉 Tạo đối tượng Documents
            Documents doc = new Documents();
            doc.setFileName(fileName);
            doc.setOriginalFileName(originalFileName);
            doc.setFileType(fileType);
            doc.setFileSize(file.getSize());
            doc.setContentType(file.getContentType());
            doc.setDownloadUrl(downloadUrl);
            doc.setCreatedUser(user.getUsername()); // hoặc ID người dùng từ token
            doc.setDescription(description);
            doc.setDeleted(0); // 0 = chưa xóa

            // Nếu cần gắn priest:
            PriestProfile priest = priestProfileRepository.findByUser_Id(user.getId()).orElse(null);
            doc.setPriest(priest);

            // 👉 Lưu DB
            documentsRepository.save(doc);

            // Trả về client
            return ResponseEntity.ok(
                    new ResponseObject(MessageConstants.OK, MessageConstants.THANH_CONG, doc)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject(MessageConstants.FAILED, MessageConstants.THAT_BAI,
                            "❌ Upload failed: " + e.getMessage()));
        }
    }

    @GetMapping("/download/{fileName}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'PRIEST')")
    public ResponseEntity<byte[]> download(
            @PathVariable String fileName,
            @RequestParam(name = "preview", required = false, defaultValue = "false") boolean preview
    ) {
        try {
            InputStream stream = minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .build()
            );

            byte[] data = stream.readAllBytes();
            stream.close();

            String contentType = URLConnection.guessContentTypeFromName(fileName);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));

            if (preview) {
                headers.set("Content-Disposition", "inline; filename=\"" + fileName + "\"");
            } else {
                headers.set("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
            }

            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}


