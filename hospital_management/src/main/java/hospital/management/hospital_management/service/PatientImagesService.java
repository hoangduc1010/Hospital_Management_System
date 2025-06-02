package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.PatientEntity;
import hospital.management.hospital_management.domain.PatientImagesEntity;
import hospital.management.hospital_management.dto.request.PatientRequest;
import hospital.management.hospital_management.repository.PatientImagesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientImagesService {
    private final PatientImagesRepository patientImagesRepository;
    public void savePatienImages(PatientRequest patientRequest, PatientEntity currentPatient){
        for(String images:patientRequest.getPatientImages()){
            PatientImagesEntity currentPatientImages=new PatientImagesEntity();
            currentPatientImages.setUrl(images);
            currentPatientImages.setPatientImages(currentPatient);
            this.patientImagesRepository.save(currentPatientImages);
        }
    }
}
