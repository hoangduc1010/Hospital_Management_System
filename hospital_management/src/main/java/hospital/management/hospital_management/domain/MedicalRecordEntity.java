package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name="medical_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicalRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToOne
    PatientEntity patient;

    @Column(columnDefinition = "MEDIUMTEXT")
    String diagnosis;

    @Column(columnDefinition = "MEDIUMTEXT")
    String symptoms;

    @Column(columnDefinition = "MEDIUMTEXT")
    String medicalHistory;

    @Column(columnDefinition = "MEDIUMTEXT")
    String allergies;

    @Column(columnDefinition = "MEDIUMTEXT")
    String treatmentPlan;

    @Column(columnDefinition = "MEDIUMTEXT")
    String doctorNote;

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
