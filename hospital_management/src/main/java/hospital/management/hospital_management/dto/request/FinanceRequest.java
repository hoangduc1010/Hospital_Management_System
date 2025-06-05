package hospital.management.hospital_management.dto.request;


import hospital.management.hospital_management.domain.FinanceDetailEntity;
import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.util.constant.PaymentTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceRequest {

    Long financeId;

    Long patientId;

    Double otherCost;

    Double insuranceCoverage;

    PaymentTypeEnum paymentType;

    Instant billingDate;

}
