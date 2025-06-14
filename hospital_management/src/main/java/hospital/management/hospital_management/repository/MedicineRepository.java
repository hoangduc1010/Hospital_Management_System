package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity,Long>, JpaSpecificationExecutor<MedicineEntity> {
    MedicineEntity findByMedicineName(String medicineName);
    MedicineEntity findByMedicineNameAndActiveIngredientAndDosageFormAndStrengthAndUnitAndManufacturerAndCountryOfOrigin(
            String medicineName,
            String activeIngredient,
            String dosageForm,
            String strength,
            String unit,
            String manufacturer,
            String countryOfOrigin
    );

}
