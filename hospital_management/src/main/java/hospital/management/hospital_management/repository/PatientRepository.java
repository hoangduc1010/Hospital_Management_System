package hospital.management.hospital_management.repository;


import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.util.constant.PatientStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<PatientEntity,Long>, JpaSpecificationExecutor<PatientEntity> {
    List<PatientEntity> findByDateOfAppointmentBetweenAndPatientStatusIn(Instant startOfDay,Instant endOfDay, List<PatientStatusEnum> patientStatusEnum);
}
