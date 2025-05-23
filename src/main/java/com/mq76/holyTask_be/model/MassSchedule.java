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
@Table(name = "mass_schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MassSchedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "parish_id")
    private Parish parish;

    @ManyToOne
    @JoinColumn(name = "priest_id")
    private PriestProfile priest;

    private String title;
    private LocalDateTime datetime;
    private String location;

    @Lob
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
