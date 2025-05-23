package com.mq76.holyTask_be.model;
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
@Table(name = "priest_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PriestProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String fullName;
    private Date ordinationDate;

    @ManyToOne
    @JoinColumn(name = "parish_id")
    private Parish parish;

    @Column(name = "updated_user")
    private String updatedUser;

    @Lob
    private String bio;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Date updatedAt;
}
