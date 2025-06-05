package hospital.management.hospital_management.repository;

import hospital.management.hospital_management.domain.PatientMedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface PatientMedicineRepository extends JpaRepository<PatientMedicineEntity,Long>, JpaSpecificationExecutor<PatientMedicineEntity> {
    Set<PatientMedicineEntity> findByPatient_IdAndIsPayment(Long patientId,Boolean isPayment);
}
