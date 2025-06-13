package hospital.management.hospital_management.helper;

import hospital.management.hospital_management.domain.DepartmentEntity;
import hospital.management.hospital_management.domain.DoctorEntity;
import hospital.management.hospital_management.domain.FinanceEntity;
import hospital.management.hospital_management.domain.NurseEntity;
import hospital.management.hospital_management.dto.response.DoctorNurseActiveResponse;
import hospital.management.hospital_management.dto.response.NumberOfDoctorAndNurseActive;
import hospital.management.hospital_management.dto.response.TotalCostIndayResponse;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.NurseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class StatisticServiceHelper {
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    public List<DoctorNurseActiveResponse> convertToDoctorNurseResponse(){
        Integer numberOfDoctorActive=this.doctorRepository.countByUser_IsActive(true);
        Integer numberOfNurseActive=this.nurseRepository.countByUser_IsActive(true);
        List<DoctorEntity> doctorEntities=this.doctorRepository.findAllByUser_IsActive(true);
        List<NurseEntity> nurseEntities=this.nurseRepository.findAllByUser_IsActive(true);
        List<DoctorNurseActiveResponse> doctorNurseActiveResponses=new ArrayList<>();
        DoctorNurseActiveResponse doctorNurseActiveResponse=new DoctorNurseActiveResponse();
        doctorNurseActiveResponse.setNumberOfDoctorAndNurseActive(new NumberOfDoctorAndNurseActive(numberOfDoctorActive,numberOfNurseActive));
        doctorNurseActiveResponses.add(doctorNurseActiveResponse);
        for(DoctorEntity doctor:doctorEntities){
            DoctorNurseActiveResponse doctorNurseActiveResponse_current=new DoctorNurseActiveResponse();
            doctorNurseActiveResponse_current.setFullName(doctor.getUser().getFullname());
            doctorNurseActiveResponse_current.setDiplomaName(doctor.getDoctorDiploma().name());
            doctorNurseActiveResponse_current.setYearOfExperience(doctor.getYearOfExperience());
            doctorNurseActiveResponse_current.setRoleName(doctor.getUser().getRole().getRoleName().name());
            List<String> departmentNames = doctor.getDepartments()
                    .stream()
                    .map(DepartmentEntity::getDepartmentName)
                    .collect(Collectors.toList());
            doctorNurseActiveResponse_current.setDepartmentName(departmentNames);
            doctorNurseActiveResponses.add(doctorNurseActiveResponse_current);
        }
        for(NurseEntity nurse:nurseEntities){
            DoctorNurseActiveResponse nurseResponse = new DoctorNurseActiveResponse();
            nurseResponse.setFullName(nurse.getUser().getFullname());
            nurseResponse.setRoleName(nurse.getUser().getRole().getRoleName().name());
            nurseResponse.setDiplomaName(nurse.getNurseDiploma().name());
            nurseResponse.setYearOfExperience(nurse.getYearOfExperience());
            List<String> departmentNames = nurse.getDepartments()
                    .stream()
                    .map(DepartmentEntity::getDepartmentName)
                    .collect(Collectors.toList());
            nurseResponse.setDepartmentName(departmentNames);
            doctorNurseActiveResponses.add(nurseResponse);
        }
        return doctorNurseActiveResponses;

    }

    public TotalCostIndayResponse convertToTotalCostInDayResponse(List<FinanceEntity> financeInDay){
        TotalCostIndayResponse totalCostIndayResponse=new TotalCostIndayResponse();
        Double totalCostInDay=0.0;
        for(FinanceEntity finance:financeInDay){
            totalCostInDay=totalCostInDay+finance.getTotalCost();
        }
        totalCostIndayResponse.setTotalCost(totalCostInDay);
        String today= LocalDate.now().toString();
        totalCostIndayResponse.setDate(today);
        return totalCostIndayResponse;
    }
}
