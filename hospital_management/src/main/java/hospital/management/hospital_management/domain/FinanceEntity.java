package hospital.management.hospital_management.domain;

import hospital.management.hospital_management.util.constant.PaymentTypeEnum;
import hospital.management.hospital_management.util.secutiry.SecurityUtil;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "finances")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long financeId;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    PatientEntity patient;

    Double otherCost;

    @Column(nullable = false)
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
