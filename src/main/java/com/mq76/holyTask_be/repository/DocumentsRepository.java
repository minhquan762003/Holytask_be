package com.mq76.holyTask_be.repository;

import com.mq76.holyTask_be.model.Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DocumentsRepository extends JpaRepository<Documents, Long> {
    List<Documents> findByDeletedFalseOrderByCreatedAtDesc();

    @Query(value = "SELECT * FROM documents WHERE priest_id = :priestId AND deleted = 0", nativeQuery = true)
    List<Documents> findByPriestId(@Param("priestId") Integer priestId);

    @Query(value = "SELECT * FROM documents WHERE original_file_name LIKE CONCAT('%', :originalFileName, '%') AND deleted = 0", nativeQuery = true)
    List<Documents> findByOriginalFileName(@Param("originalFileName") String originalFileName);



}
