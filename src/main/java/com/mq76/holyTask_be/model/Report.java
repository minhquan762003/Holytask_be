package com.mq76.holyTask_be.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reports")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "priest_id")
    private PriestProfile priest;

    @Enumerated(EnumType.STRING)
    private ReportType type;

    private String title;

    @Lob
    private String content;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

