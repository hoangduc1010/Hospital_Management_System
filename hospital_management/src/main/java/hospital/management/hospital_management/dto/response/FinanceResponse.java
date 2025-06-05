package hospital.management.hospital_management.dto.response;



import hospital.management.hospital_management.util.constant.PaymentTypeEnum;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceResponse {
    Long financeId;

    String patientName;

    Double totalPrice;

    PaymentTypeEnum paymentType;

    Instant billingDate;

    Instant timeOfAdmission;

    Instant timeOfDischarge;

    List<FinanceDetailResponse> financeDetailResponse;
}
