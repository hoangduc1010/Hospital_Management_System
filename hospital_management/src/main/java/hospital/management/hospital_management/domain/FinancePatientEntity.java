package hospital.management.hospital_management.domain;

import hospital.management.hospital_management.util.constant.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "finance_patient")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinancePatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long financeId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    PatientEntity patient;

    Double otherCost;

    Double totalCost;

    Double insuranceCoverage;

    @Enumerated(EnumType.STRING)
    PaymentTypeEnum paymentType;

    Instant billingDate;

    @OneToMany(mappedBy = "finance")
    Set<FinanceDetailEntity> financeDetails;

    Boolean isPayment;

    @PrePersist
    public void billingDateBeforeCreated() {
        this.billingDate=Instant.now();
    }

}
