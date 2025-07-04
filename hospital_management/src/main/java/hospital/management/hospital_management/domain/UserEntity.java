package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.GenderEnum;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name="users")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String fullname;

    String username;

    String password;

    String phoneNumber;


    String dob;

    @Enumerated(EnumType.STRING)
    GenderEnum gender;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    RoleEntity role;

    @OneToOne(mappedBy = "user")
    DoctorEntity doctor;

    @OneToOne(mappedBy = "user")
    NurseEntity nurse;

    @OneToOne(mappedBy = "user")
    PatientEntity patient;

    Boolean isActive;

    String address;

    String avatar;


    @Column(columnDefinition = "MEDIUMTEXT")
    String refreshToken;

    Instant createdAt;

    Instant updatedAt;

    String createdBy;

    String updatedBy;

    @PrePersist
    public void userBeforeCreated() {
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";
        this.createdAt = Instant.now();
    }

    @PreUpdate
    public void userBeforeUpdated() {
        this.updatedAt = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent() == true ?
                SecurityUtil.getCurrentUserLogin().get() : "";

    }


}
