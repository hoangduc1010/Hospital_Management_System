package hospital.management.hospital_management.service;

import hospital.management.hospital_management.dto.response.DoctorNurseActiveResponse;
import hospital.management.hospital_management.helper.StatisticServiceHelper;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.NurseRepository;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticService {
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;
    private final StatisticServiceHelper statisticServiceHelper;
    public List<DoctorNurseActiveResponse> getAllDoctorAndNurseAreActive(){
        return this.statisticServiceHelper.convertToDoctorNurseResponse();
    }
}
