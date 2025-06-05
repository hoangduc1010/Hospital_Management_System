package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@Entity
@Table(name="patient_medicine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PatientMedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false)
    PatientEntity patient;

    @ManyToOne
    @JoinColumn(name = "medicine_id",nullable = false)
    MedicineEntity medicine;

    Integer quantity;

    Instant dateOfPrescribe;

    Boolean isPayment;

    @PrePersist
    public void prescribeBeforeCreated() {
        this.dateOfPrescribe=Instant.now();
    }

    @PreUpdate
    public void prescribeBeforeUpdated() {
        this.dateOfPrescribe=Instant.now();

    }

}
