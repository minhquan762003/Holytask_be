package com.mq76.holyTask_be.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "parishioner_profiles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParishionerProfile {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fullName;
    private Date dateOfBirth;
    private String address;

    @ManyToOne
    @JoinColumn(name = "parish_group_id")
    private ParishGroup parishGroup;

    @Column(name = "phone_number")
    private String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "sub_parish_id")
    private SubParish subParish;

    @Column(name = "updated_user")
    private String updatedUser;

    @Column(name = "created_user")
    private String createdUser;

    @Column(name = "img_url")
    private String imgUrl;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;
}

