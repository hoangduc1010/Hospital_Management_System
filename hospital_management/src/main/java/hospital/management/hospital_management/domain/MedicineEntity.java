package hospital.management.hospital_management.domain;


import hospital.management.hospital_management.util.constant.MedicineCategoryEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="medicines")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MedicineEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String medicineName;

    String activeIngredient;

    String dosageForm;

    String strength;

    String unit;

    Integer quantityInStock;

    Double sellPrice;

    Double purchasePrice;

    String manufacturer;

    String countryOfOrigin;

    Instant expiryDate;

    String notes;

    @Enumerated(EnumType.STRING)
    MedicineCategoryEnum medicineCategory;

    Boolean isActive;

    @ManyToMany(mappedBy = "medicineSet")
    Set<FinanceDetailEntity> financeDetailSet;

    @OneToMany(mappedBy = "medicine")
    Set<PatientMedicineEntity> prescriptions;


}
