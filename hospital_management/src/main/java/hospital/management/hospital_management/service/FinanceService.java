package hospital.management.hospital_management.service;


import hospital.management.hospital_management.domain.*;
import hospital.management.hospital_management.dto.request.FinanceRequest;
import hospital.management.hospital_management.dto.response.FinanceResponse;
import hospital.management.hospital_management.helper.FinanceServiceHelper;
import hospital.management.hospital_management.repository.*;
import hospital.management.hospital_management.util.error.CustomException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FinanceService {
    private final MedicineRepository medicineRepository;
    private final FinanceRepository financeRepository;
    private final FinanceDetailRepository financeDetailRepository;
    private final FinanceServiceHelper financeServiceHelper;
    private final PatientRepository patientRepository;
    private final PatientMedicineRepository patientMedicineRepository;

    @Transactional
    public FinanceResponse payment(FinanceRequest financeRequest) throws CustomException {


        this.financeServiceHelper.checkValidInfoCreateFinance(financeRequest);

        PatientEntity currentPatient=this.patientRepository.findById(financeRequest.getPatientId()).get();
        FinancePatientEntity currentFinancePatientEntity = currentPatient.getFinanceSet()
                .stream()
                .filter(f -> !f.getIsPayment())
                .findFirst()
                .orElse(null);
        currentFinancePatientEntity.setPatient(currentPatient);
        FinanceDetailEntity currentFinanceDetailEntity=new FinanceDetailEntity();
        Set<MedicineEntity> medicineEntitySet=new HashSet<>();



        currentFinancePatientEntity.setPaymentType(financeRequest.getPaymentType());
        currentFinancePatientEntity.setInsuranceCoverage(financeRequest.getInsuranceCoverage());
        currentFinanceDetailEntity.setAppointmentsType(currentPatient.getAppointmentsType());

        Double totalCost=0.0;
        totalCost=totalCost+currentPatient.getAppointmentsType().getPrice()+financeRequest.getOtherCost();
        Set<PatientMedicineEntity> currentPatientMedicineEntity=this.patientMedicineRepository.findByPatient_IdAndIsPayment(currentPatient.getId(),false);

        for(PatientMedicineEntity patientMedicineEntity:currentPatientMedicineEntity){
            if(patientMedicineEntity.getIsPayment()==false){
                MedicineEntity currentMedicine=patientMedicineEntity.getMedicine();
                totalCost=totalCost+(currentMedicine.getSellPrice()*patientMedicineEntity.getQuantity());
                medicineEntitySet.add(patientMedicineEntity.getMedicine());
                patientMedicineEntity.setIsPayment(false);
                this.patientMedicineRepository.save(patientMedicineEntity);
            }
        }
        totalCost=totalCost-(totalCost * financeRequest.getInsuranceCoverage() / 100);
        currentFinancePatientEntity.setTotalCost(totalCost);
        currentFinancePatientEntity.setIsPayment(true);
        this.financeRepository.save(currentFinancePatientEntity);
        this.patientRepository.save(currentPatient);
        currentFinanceDetailEntity.setMedicineSet(medicineEntitySet);
        currentFinanceDetailEntity.setFinance(currentFinancePatientEntity);
        this.financeDetailRepository.save(currentFinanceDetailEntity);
        FinanceResponse financeResponse=this.financeServiceHelper.convertToFinanceResponse(currentPatient);
        return financeResponse;
    }
}
