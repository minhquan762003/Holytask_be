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
@Table(name = "parishioner_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParishionerProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;
    private LocalDate dateOfBirth;
    private String address;

    @ManyToOne
    @JoinColumn(name = "sub_parish_id")
    private SubParish subParish;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}

