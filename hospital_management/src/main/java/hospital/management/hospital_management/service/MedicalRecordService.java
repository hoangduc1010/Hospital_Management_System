package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.MedicalRecordEntity;
import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.dto.request.PatientRequest;
import hospital.management.hospital_management.repository.MedicalRecordRepository;
import hospital.management.hospital_management.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;
    public MedicalRecordEntity saveMedicalRecordWithPatient(PatientRequest patientRequest){
        PatientEntity currentPatient=this.patientRepository.findById(patientRequest.getPatientId()).get();
        MedicalRecordEntity currentMedicalRecord=currentPatient.getMedicalRecord() == null ? new MedicalRecordEntity() : currentPatient.getMedicalRecord();
        currentMedicalRecord.setPatient(currentPatient);
        currentPatient.setMedicalRecord(currentMedicalRecord);
        modelMapper.getConfiguration().setAmbiguityIgnored(true).setSkipNullEnabled(true);
        if(patientRequest.getMedicalRecord()!=null){
            modelMapper.map(patientRequest.getMedicalRecord(),currentMedicalRecord);
        }
        this.medicalRecordRepository.save(currentMedicalRecord);
        return currentMedicalRecord;
    }
}
