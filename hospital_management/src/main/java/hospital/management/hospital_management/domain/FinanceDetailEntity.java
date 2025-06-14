package hospital.management.hospital_management.domain;

import hospital.management.hospital_management.util.constant.AppointmentsTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "finance_details")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FinanceDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long financeDetailId;

    @ManyToOne
    @JoinColumn(name = "finance_id", nullable = false)
    FinancePatientEntity finance;

    @ManyToMany
    @JoinTable(name="finance_patient_medicine",
    joinColumns = @JoinColumn(name="finance_detail_id"),
    inverseJoinColumns = @JoinColumn(name="medicine_id"))
    Set<MedicineEntity> medicineSet;

    @Enumerated(EnumType.STRING)
    AppointmentsTypeEnum appointmentsType;




}
