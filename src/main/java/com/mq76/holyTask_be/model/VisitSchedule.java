package com.mq76.holyTask_be.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "visit_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisitSchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "priest_id")
    private PriestProfile priest;

    @Enumerated(EnumType.STRING)
    private VisitType visitType;
//    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX", timezone = "Asia/Ho_Chi_Minh")
    private Date datetime;
    private String address;

    @Column(name = "created_user")
    private String createdUser;

    @Lob
    private String notes;

    @Column(name = "headline")
    private String headline;

    @Column(name = "status")
    private Integer status;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @CreationTimestamp
    private Date createdAt;
    @UpdateTimestamp
    private Date updatedAt;
}

