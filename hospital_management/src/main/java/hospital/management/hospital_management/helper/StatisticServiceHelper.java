package hospital.management.hospital_management.helper;

import hospital.management.hospital_management.domain.*;
import hospital.management.hospital_management.dto.request.TotalCostFromDateToRequest;
import hospital.management.hospital_management.dto.response.*;
import hospital.management.hospital_management.repository.DoctorRepository;
import hospital.management.hospital_management.repository.NurseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public TotalCostIndayResponse convertToTotalCostInDayResponse(List<FinancePatientEntity> financePatientInDay,List<FinanceMedicineEntity> financeMedicineInDay){
        TotalCostIndayResponse totalCostIndayResponse=new TotalCostIndayResponse();
        Double totalCostFromPatientsInDay=0.0;
        Double totalCostFromBuyInMedicinesInDay=0.0;

        for(FinancePatientEntity finance:financePatientInDay){
            totalCostFromPatientsInDay=totalCostFromPatientsInDay+finance.getTotalCost();
        }

        for(FinanceMedicineEntity financeMedicineEntity:financeMedicineInDay){
            totalCostFromBuyInMedicinesInDay=totalCostFromBuyInMedicinesInDay+financeMedicineEntity.getTotalCost();
        }

        totalCostIndayResponse.setTotalCostPatient(totalCostFromPatientsInDay);
        totalCostIndayResponse.setTotalCostBuyInMedicines(totalCostFromBuyInMedicinesInDay);
        String today= LocalDate.now().toString();
        totalCostIndayResponse.setDate(today);
        return totalCostIndayResponse;
    }

    public TotalCostFromDateToResponse convertToTotalCostFromDateToResponse(List<FinancePatientEntity> financePatientFromDay,
                                                                            List<FinanceMedicineEntity> financeMedicineFromDay,
                                                                            TotalCostFromDateToRequest totalCostFromDateToRequest){
        TotalCostFromDateToResponse totalCostFromDateToResponse=new TotalCostFromDateToResponse();
        Double totalCostPatientsFromDateTo=0.0;
        Double totalCostMedicinesFromDateTo=0.0;

        for(FinancePatientEntity financePatient:financePatientFromDay){
            totalCostPatientsFromDateTo=totalCostPatientsFromDateTo+financePatient.getTotalCost();
        }
        for(FinanceMedicineEntity financeMedicine:financeMedicineFromDay){
            totalCostMedicinesFromDateTo=totalCostMedicinesFromDateTo+financeMedicine.getTotalCost();
        }

        if(totalCostFromDateToRequest.getStartDate()!=null){
            totalCostFromDateToResponse.setFromDate(totalCostFromDateToRequest.getStartDate());
        }
        if(totalCostFromDateToRequest.getEndDate()!=null){
            totalCostFromDateToResponse.setToDate(totalCostFromDateToRequest.getEndDate());
        }
        totalCostFromDateToResponse.setTotalCostFromPatients(totalCostPatientsFromDateTo);
        totalCostFromDateToResponse.setTotalCostFromMedicines(totalCostMedicinesFromDateTo);
        return totalCostFromDateToResponse;
    }

}
